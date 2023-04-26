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
    public SendBatchResponse sendBatch(@RequestPayload SendBatchRequest request) throws JSONException, RemoteException {
        // 1. obtener los datos de los clientes desde SOAP
        SendBatchResponse response = new SendBatchResponse();
        String listJSON = request.getListJSON();
        String token = request.getToken();
        try {
//             2. conectarse al servidor de autenticación REST
            String res = Rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/validate", "GET", token);
            if (!res.equals("")) {
                try {
                    JSONObject jsonRes = new JSONObject(res);
                    boolean response_str = jsonRes.getBoolean("message");
                    if (response_str) {
                        // crear sublote para enviar al servidor RMI
                        String idSubBatch = String.valueOf(new Date().getTime());
                        JSONArray jsonArr = new JSONArray(listJSON); //[{},{},{}]
                        String type = "";
                        ArrayList<File> archivosList = new ArrayList<>();
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
//                             {"idFile":1,
//                             "base64":"123456789",
//                             "fileName":"ejemplo",
//                             "fileExtension":".docx",
//                             "size":13,
//                             "checksum":"aaa"
//                             }
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
                            archivosList.add(file);
                        }
                        // todo: conectarse al servidor rest con un metodo de getUserIDByToken
//                        String resUserID = Rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/", "GET", token);
                        String fakeUserid = "2";
                        File[] archivos = archivosList.toArray(new File[archivosList.size()]);
                        SubBatch fullBatch = new SubBatch(idSubBatch, fakeUserid, archivos);
                        System.out.println("\n------------------------ FULL BATCH ------------------------\n");
                        System.out.println(fullBatch);
                        System.out.println();
                        List<SubBatch> subBatches = DivideArray.splitArray(fullBatch);
                        SubBatch batch1 = subBatches.get(0);
                        SubBatch batch2 = subBatches.get(1);
                        SubBatch batch3 = subBatches.get(2);

                        InterfaceRMI nodo1 = ProducingWebServiceApplication.nodo1;
//                        // contratoRMI nodo2 = ProducingWebServiceApplication.nodo2;
//                        // contratoRMI nodo3 = ProducingWebServiceApplication.nodo3;

                        SubBatch batchPDF1;
                        SubBatch batchPDF2;
                        SubBatch batchPDF3;
                        System.out.println("Archivos enviados a conversión...");
                        if (type.equals("URL")) {
                            batchPDF1 = nodo1.conversionURL(batch1);
                            // batchPDF2 = nodo2.conversionURL(batch2);
                            // batchPDF3 = nodo3.conversionURL(batch3);
                        } else {
                            batchPDF1 = nodo1.conversionOffice(batch1);
                            // batchPDF2 = nodo2.conversionOffice(batch2);
                            // batchPDF3 = nodo3.conversionOffice(batch3);
                        }
                        System.out.println("\nArchivos convertidos");
                        System.out.println();
                        System.out.println(Arrays.toString(batchPDF1.files));
                        // todo: enviar archivos al servidor de archivos para que nos devuelva el link de descarga
                        String resDownloadURL = Rest.connect("http://bd.bucaramanga.upb.edu.co:4000/decode","POST",batchPDF1.toString());
//                        Rest.connect("http://bd.bucaramanga.upb.edu.co:4000/decode","POST",batchPDF2.toString());
//                        Rest.connect("http://bd.bucaramanga.upb.edu.co:4000/decode","POST",batchPDF3.toString());
//                        try
                        response.setSuccessful(true);
                        response.setResponse("Archivos convertidos");
                        response.setDownloadPath("Aquí estará el link de descarga");
                    } else {
                        response.setSuccessful(false);
                        response.setResponse(String.valueOf(response_str));
                        response.setDownloadPath("");
                    }
                } catch (JSONException e) {
                    try {
                        JSONObject jsonRes = new JSONObject(res);
                        response.setDownloadPath("");
                        response.setSuccessful(false);
                        response.setResponse(jsonRes.getString("error"));
                    } catch (JSONException e2) {
                        // in case of an unexpected error
                        response.setDownloadPath("");
                        response.setSuccessful(false);
                        response.setResponse(e2.toString());
                    }
                }
            } else {
                response.setDownloadPath("");
                response.setSuccessful(false);
                response.setResponse("Hubo un error al enviar los archivos");
            }
        } catch (IOException e) {
            response.setDownloadPath("");
            response.setSuccessful(false);
            response.setResponse("Hubo un error al enviar los archivos: " + e.toString());
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBatchDetailsRequest")
    @ResponsePayload
    public GetBatchDetailsResponse getBatchDetails(@RequestPayload GetBatchDetailsRequest request) {
        GetBatchDetailsResponse response = new GetBatchDetailsResponse();

        String userID = request.getUserID();
        String token = request.getToken();

        // TODO: conexión a la base de datos de Yireth y Andrey através de REST
        // para validar el token, si es valido continua, sino error de autenticación

        if (fakeToken.equals(token)) {
            try {
                String res = Rest.connect("http://bd.bucaramanga.upb.edu.co:3000/lote/uploadLotes", "POST", "idUsuario=" + userID);
                if (res.equals("")) {
                    response.setResponse("Lote no encontrado");
                    response.setSuccessful(false);
                } else {
                    JSONArray jsonArr = new JSONArray(res);
                    JSONObject jsonObj = jsonArr.getJSONObject(0);
                    String dateCreated = jsonObj.getString("createdAt");
                    response.setDateCreated(dateCreated);
                    response.setFileQuantity(jsonObj.getInt("numeroArchivos"));
                    String vigencia = jsonObj.getString("vigencia");
                    // todo: do the substraction of dates (vigencia-dateCreated) and set it in setTimeExpiration
                    response.setTimeExpiration(vigencia);
                }
            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
//         If the user is not authenticated, return an error message
            response.setResponse("Token inválido o expirado");
            response.setSuccessful(false);
        }
        return response;
    }

    private String timeFormatter(LocalDateTime time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(time);
    }

    private String timeSubtract(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        String days = String.valueOf(ChronoUnit.DAYS.between(now, time));
        String hours = String.valueOf(ChronoUnit.HOURS.between(now, time));
        String minutes = String.valueOf(ChronoUnit.MINUTES.between(now, time));
        return days + " days, " + hours + " hours, " + minutes + " minutes.";

    }


}