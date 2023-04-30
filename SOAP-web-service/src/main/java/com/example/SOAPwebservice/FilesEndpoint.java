package com.example.SOAPwebservice;

import io.spring.guides.gs_producing_web_service.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Endpoint
public class FilesEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    static final String fakeToken = "abc123";
    static final String fakelist = "abc123";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sendBatchRequest")
    @ResponsePayload
    public SendBatchResponse sendBatch(@RequestPayload SendBatchRequest request) throws JSONException, IOException {
        System.out.println("\n----------------Inicio método SendBatch--------------------");
        SendBatchResponse response = new SendBatchResponse();
        response.setDownloadPath("");
        response.setSuccessful(false);
        response.setResponse("Ocurrió un error");

        String listJSON = request.getListJSON();
        String token = request.getToken();
        System.out.println("Iniciando petición al servidor de autenticación");
        String res = Rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/get-userid", "GET", token);
        System.out.println("Respuesta del servidor de autenticación: " + res);
        if (res != null) {
            JSONObject jsonResUser = new JSONObject(res);
            if (jsonResUser.has("id_user")) {
                String userID = jsonResUser.getString("id_user");
                String idSubBatch = String.valueOf(new Date().getTime());
                JSONArray jsonArr = new JSONArray(listJSON); //[{},{},{}]
                String type = "";
                ArrayList<File> fileList = new ArrayList<>();
                System.out.println("\nCreado array de archivos según lo enviado por el cliente:");
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    int idFile = jsonObj.getInt("idFile");
                    type = jsonObj.getString("fileExtension");
                    String base64 = jsonObj.getString("base64"); //if url this contains the link
                    String fileName = jsonObj.getString("fileName");
                    fileName += type;
                    int size = jsonObj.getInt("size");
                    String checksum = jsonObj.getString("checksum");
                    File file;
                    if (type.equals("URL")) {
                        file = new File(idSubBatch, base64, idFile);
                    } else {
                        file = new File(idSubBatch, base64, fileName, checksum);
                    }
                    file.size = size;
                    fileList.add(file);
                    System.out.println(file.name);
                }
                File[] archivos = fileList.toArray(new File[fileList.size()]);
                SubBatch fullBatch = new SubBatch(idSubBatch, userID, archivos);
                System.out.println("\nDividiendo cargas para cada nodo:");
                List<SubBatch> subBatches = Balancer.divideSubBatch(fullBatch);
                SubBatch batch1 = subBatches.get(0);
                SubBatch batch2 = subBatches.get(1);
                SubBatch batch3 = subBatches.get(2);
                System.out.println("Nodo 1: " + batch1.files.length + " archivos");
                System.out.println("Nodo 2: " + batch2.files.length + " archivos");
                System.out.println("Nodo 3: " + batch3.files.length + " archivos");

                System.out.println("\nConexión al servidor RMI para conversión de archivos");
                InterfaceRMI nodo1 = ProducingWebServiceApplication.nodo1;
                InterfaceRMI nodo2 = ProducingWebServiceApplication.nodo2;
                InterfaceRMI nodo3 = ProducingWebServiceApplication.nodo3;

                SubBatch batchPDF1;
                SubBatch batchPDF2;
                SubBatch batchPDF3;
                if (type.equals("URL")) {
                    batchPDF1 = nodo1.conversionURL(batch1);
                    batchPDF2 = nodo2.conversionURL(batch2);
                    batchPDF3 = nodo3.conversionURL(batch3);
                } else {
                    batchPDF1 = nodo1.conversionOffice(batch1);
                    batchPDF2 = nodo2.conversionOffice(batch2);
                    batchPDF3 = nodo3.conversionOffice(batch3);
                }

                System.out.println("\nConexión al servidor de archivos para almacenamiento");
                String resFileServer1 = Rest.connect("http://bd.bucaramanga.upb.edu.co:4000/decode", "POST", batchPDF1.toString());
                String resFileServer2 = Rest.connect("http://bd.bucaramanga.upb.edu.co:4000/decode", "POST", batchPDF2.toString());
                String resFileServer3 = Rest.connect("http://bd.bucaramanga.upb.edu.co:4000/decode", "POST", batchPDF3.toString());
                System.out.println("Respuesta del servidor nodo 1: " + resFileServer1);
                System.out.println("Respuesta del servidor nodo 2: " + resFileServer2);
                System.out.println("Respuesta del servidor nodo 3: " + resFileServer3);
                if (resFileServer1 != null && resFileServer2 != null && resFileServer3 != null) {
                    response.setSuccessful(true);
                    response.setResponse("Archivos convertidos");
                    response.setDownloadPath("http://bd.bucaramanga.upb.edu.co:4000/download_batch/" + userID + "/" + idSubBatch);
                    System.out.println("\n----------------Metodo finalizado correctamente--------------------");
                }
            } else if (jsonResUser.has("error")) {
                response.setResponse(jsonResUser.getString("error"));
                System.out.println("\n----------------Ocurrió un error--------------------");
            }
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBatchDetailsRequest")
    @ResponsePayload
    public GetBatchDetailsResponse getBatchDetails(@RequestPayload GetBatchDetailsRequest request) throws JSONException, IOException {
        GetBatchDetailsResponse response = new GetBatchDetailsResponse();
        response.setSuccessful(false);
        response.setBatchesList("[]");
        response.setResponse("Ocurrió un error");

        String token = request.getToken();
        String resUserID = Rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/get-userid", "GET", token);
        if (resUserID != null) {
            JSONObject jsonResUser = new JSONObject(resUserID);

            if (jsonResUser.has("id_user")) {
                String userID = jsonResUser.getString("id_user");
                JSONObject params = new JSONObject();
                params.put("userId", userID);
                    /*[
                    {
                        "files": [
                        {
                            "fileName": "arepa",
                                "size": 42,
                                "filePath": "ruta en rust"
                        },
                        {
                            "fileName": "empanada",
                                "size": 13,
                                "filePath": "ruta en rust"
                        }
                        ],
                        "_id": "644973588753a2a164f97d9d",
                        "userId": "085819fd9161976b7461ccecf20a7885",
                        "numberFiles": 2,
                        "batchPath": "C:\\Users\\Henry\\Documents\\GitHub",
                        "validity": "2023-03-30T00:00:00.000Z",
                        "status": true,
                        "createdAt": "2023-04-26T18:54:16.527Z",
                        "updatedAt": "2023-04-26T18:54:16.527Z"
                    }
                    ]*/
                String res = Rest.connect("http://bd.bucaramanga.upb.edu.co:3000/batch/callBatches", "POST", params.toString());
                if (!res.equals("")) {
                    response.setSuccessful(true);
                    response.setBatchesList(res);
                    response.setResponse("Se obtuvieron los lotes de archivos correctamente");
                }

            } else if (jsonResUser.has("error")) {
                String error = jsonResUser.getString("error");
                response.setResponse(error);
            }
        }
        return response;
    }

}