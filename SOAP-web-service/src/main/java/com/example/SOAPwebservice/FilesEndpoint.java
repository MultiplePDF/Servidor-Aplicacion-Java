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

@Endpoint
public class FilesEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    static final String fakeToken = "abc123";
    static final String fakelist = "abc123";
    static final String fakeid = "2";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sendBatchRequest")
    @ResponsePayload
    public SendBatchResponse sendBatch(@RequestPayload SendBatchRequest request) throws JSONException, RemoteException {
        SendBatchResponse response = new SendBatchResponse();

        String listJSON = request.getListJSON();
        String token = request.getToken();

        // TODO: conexión a la base de datos de Yireth y Andrey através de REST
        // para validar el token, si es valido continua, sino error de autenticación
        if (fakeToken.equals(token)) {
            String idSubBatch = String.valueOf(new Date().getTime());
            JSONArray jsonArr = new JSONArray(listJSON); //[{},{},{}]
            ArrayList<Archivo> archivos1List = new ArrayList<Archivo>();
//            ArrayList<Archivo> archivos2List = new ArrayList<Archivo>();
//            ArrayList<Archivo>archivos3List = new ArrayList<Archivo>();
            String type = "";
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
//                System.out.println(jsonObj);
                // {"idFile":1, - int
                // "base64":"123456789", - string
                // "fileName":"ejemplo", - string
                // "fileExtension":".docx", - string si es url poner "URL"
                // "size":13 - int en kilobytes
                // }
                int idFile = jsonObj.getInt("idFile");
                type = jsonObj.getString("fileExtension");
                String base64 = jsonObj.getString("base64"); //if url this contains the link
                String fileName = jsonObj.getString("fileName");
                int size = jsonObj.getInt("size");
                Archivo file;
                if (type.equals("URL")) {
                    file = new Archivo(idSubBatch, base64, idFile);
                } else {
                    file = new Archivo(idSubBatch, base64, fileName);
                }

                archivos1List.add(file);
//                archivos2List.add(file);
//                archivos3List.add(file);

            }
            Archivo[] archivos1 = archivos1List.toArray(new Archivo[archivos1List.size()]);
            System.out.println("array");
            for (Archivo x: archivos1) {
                System.out.println(x.toString());
            }
//            Archivo[] archivos2 = archivos2List.toArray(new Archivo[archivos2List.size()]);
//            Archivo[] archivos3 = archivos3List.toArray(new Archivo[archivos3List.size()]);

            Sublote batch1 = new Sublote(idSubBatch, fakeid, archivos1);
//            Sublote batch2 = new Sublote(idSubBatch, fakeid, archivos2);
//            Sublote batch3 = new Sublote(idSubBatch, fakeid, archivos3);

            contratoRMI nodo1 = ProducingWebServiceApplication.nodo1;
            contratoRMI nodo2 = ProducingWebServiceApplication.nodo2;
            contratoRMI nodo3 = ProducingWebServiceApplication.nodo3;

            if (type.equals("URL")) {
                nodo1.conversionURL(batch1);
//                nodo2.conversionURL(batch2);
//                nodo3.conversionURL(batch3);
            } else {
                nodo1.conversionOffice(batch1);
//                nodo2.conversionOffice(batch2);
//                nodo3.conversionOffice(batch3);
            }
            response.setSuccess("Files sent to conversion");
            // todo: redirigir al metodo para descargar los archivos
        } else {
            response.setSuccess("You session expired, please log in again");
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
            RestConnect rest = new RestConnect();
            try {
                String res = rest.connect("http://bd.bucaramanga.upb.edu.co:3000/lote/uploadLotes", "POST", "idUsuario=" + userID);
                if (res.equals("")) {
                    response.setSuccess("Batch not found");
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
            } catch (IOException e) {
                response.setSuccess("An error occurred during connection to the server");
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
//         If the user is not authenticated, return an error message
            response.setSuccess("You session expired, please log in again");
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