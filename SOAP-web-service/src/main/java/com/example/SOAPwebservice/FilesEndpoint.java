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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Endpoint
public class FilesEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    //	Batch methods
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sendfileRequest")
    @ResponsePayload
    public SendBatchResponse sendFile(@RequestPayload SendBatchRequest request) {
        SendBatchResponse response = new SendBatchResponse();

        String listJSON = request.getListJSON();
        String token = request.getToken();

        // TODO: conexión a la base de datos de Yireth y Andrey através de REST
        // para validar el token, si es valido continua, sino error de autenticación

        // TODO: CONEXION RMI

        // pseudocodigo en comentarios

//	    if (token es válido) {
// enviar a RMI
        response.setSuccess("Archivo enviado a conversión");
//			else{
        response.setSuccess("File not found");
//			}
//	    } else {

        response.setSuccess("You session expired, please log in again");
//	    }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "batchRequest")
    @ResponsePayload
    public BatchResponse getBatchDetails(@RequestPayload BatchRequest request) {
        BatchResponse response = new BatchResponse();

        String userID = request.getUserID();
        String token = request.getToken();

        // TODO: conexión a la base de datos de Yireth y Andrey através de REST
        // para validar el token, si es valido continua, sino error de autenticación

        // if (token es válido) {
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
        //} else {
        // If the user is not authenticated, return an error message
        // response.setSuccess("You session expired, please log in again");
//	    }
        return response;
    }

    // File methods
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fileRequest")
    @ResponsePayload
    public FileResponse getFileDetails(@RequestPayload FileRequest request) {
        FileResponse response = new FileResponse();

        String fileID = request.getFileID();
        String token = request.getToken();

        // TODO: conexión a la base de datos de Yireth y Andrey através de REST
        // para validar el token, si es valido continua, sino error de autenticación

        // TODO: conexión a la base de datos de Henry, Wilson y Mario através de REST
        // y devolver toda la información del archivo seleccionado

        // pseudocodigo en comentarios

//	    if (token es válido) {
//			if (fileID está en la base de datos)
        response.setFilename("hola.pdf");
        response.setPath("mulltiplepdf.com/hola.pdf");
        response.setSize(2.5F);
//			else{
        response.setSuccess("File not found");
//			}
//	    } else {
        // If the user is not authenticated, return an error message
        response.setFilename(null); //probably not needed
        response.setPath(null); //probably not needed
        response.setSize(0); //probably not needed
        response.setSuccess("You session expired, please log in again");
//	    }

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