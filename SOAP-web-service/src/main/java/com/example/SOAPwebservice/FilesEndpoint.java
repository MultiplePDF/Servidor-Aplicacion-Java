package com.example.SOAPwebservice;

// Spring
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

// Exceptions
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.json.JSONException;

// Java Net
import java.net.URL;

// JSON
import org.json.JSONArray;
import org.json.JSONObject;

// RMI
import java.rmi.Naming;

// Java Utils
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// SOAP
import io.spring.guides.gs_producing_web_service.GetBatchDetailsRequest;
import io.spring.guides.gs_producing_web_service.GetBatchDetailsResponse;
import io.spring.guides.gs_producing_web_service.SendBatchRequest;
import io.spring.guides.gs_producing_web_service.SendBatchResponse;

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
                        System.out.println(file.name);
                    }
                    file.size = size;
                    fileList.add(file);
                }
                File[] archivos = fileList.toArray(new File[fileList.size()]);
                SubBatch fullBatch = new SubBatch(idSubBatch, userID, archivos);

                System.out.println("\nIniciando conexión al servidor RMI para conversión de archivos");

                List<InterfaceRMI> availableNodes = new ArrayList<>();
                try {
                    if (_isValidURL("rmi://nodo1.bucaramanga.upb.edu.co:1099/convertidor"))
                        availableNodes.add((InterfaceRMI) Naming.lookup("rmi://nodo1.bucaramanga.upb.edu.co:1099/convertidor"));

                    if (_isValidURL("rmi://nodo2.bucaramanga.upb.edu.co:1099/convertidor"))
                        availableNodes.add((InterfaceRMI) Naming.lookup("rmi://nodo2.bucaramanga.upb.edu.co:1099/convertidor"));

                    if (_isValidURL("rmi://nodo3.bucaramanga.upb.edu.co:1099/convertidor"))
                        availableNodes.add((InterfaceRMI) Naming.lookup("rmi://nodo3.bucaramanga.upb.edu.co:1099/convertidor"));

                    if (availableNodes.size() == 0) {
                        response.setResponse("No se ha encontrado ningun nodo disponible.");
                        System.out.println("No se ha encontrado ningun nodo disponible.");
                        System.out.println("\n----------------FIN--------------------");
                        return response;
                    }
                    System.out.println("Conexión exitosa!");

                    System.out.println("\nDividiendo cargas para cada nodo:");
                    List<SubBatch> subBatches = Balancer.divideSubBatch(fullBatch, availableNodes.size());

                    List<SubBatch> convertedSubBatches = new ArrayList<>();
                    for (int i = 0; i < availableNodes.size(); ++i) {
                        InterfaceRMI availableNode = availableNodes.get(i);
                        SubBatch convertedSubBatch;

                        if (type.equals("URL"))
                            convertedSubBatch = availableNode.conversionURL(subBatches.get(i));
                        else
                            convertedSubBatch = availableNode.conversionOffice(subBatches.get(i));

                        convertedSubBatches.add(convertedSubBatch);
                    }

                    for (int i = 0; i < convertedSubBatches.size(); ++i)
                        System.out.println("ID batch convertido a PDF " + i + ": " + convertedSubBatches.get(i).subBatchID);

                    List<File> convertedFiles = new ArrayList<>();
                    for (SubBatch convertedSubBatch : convertedSubBatches) {
                        File[] convertedSubBatchFiles = convertedSubBatch.files;
                        for (File convertedSubBatchFile : convertedSubBatchFiles)
                            convertedFiles.add(convertedSubBatchFile);
                    }

                    SubBatch batchToSend = new SubBatch(idSubBatch, userID, (File[]) convertedFiles.toArray());
                    System.out.println("\nUnificado los " + convertedSubBatches.size() + " batches en 1 solo batch de: " + batchToSend.files.length + "archivos");
                    System.out.println("\nConexión al servidor de archivos para almacenamiento");
                    String resFileServer = Rest.connect("http://bd.bucaramanga.upb.edu.co:4000/decode", "POST", batchToSend.toString());
                    System.out.println("Respuesta del servidor de archivos nodo 1: " + resFileServer);

                    if (resFileServer != null) {
                        response.setSuccessful(true);
                        response.setResponse("Archivos convertidos");
                        response.setDownloadPath("http://bd.bucaramanga.upb.edu.co:4000/download_batch/" + userID + "/" + idSubBatch);
                        System.out.println("\n----------------Metodo finalizado correctamente--------------------");
                    }

                } catch (NotBoundException | RemoteException | MalformedURLException e) {
                    System.out.println("Conexión RMI fallida: " + e);
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

    private boolean _isValidURL(String url) {
        try {
            new URL(url).toURI();
            System.out.println("URL válido");
            return true;
        } catch (Exception e) {
            System.out.println("URL inválido");
            return false;
        }
    }
}
