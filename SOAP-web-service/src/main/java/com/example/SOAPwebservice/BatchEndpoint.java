package com.example.SOAPwebservice;

import io.spring.guides.gs_producing_web_service.BatchRequest;
import io.spring.guides.gs_producing_web_service.BatchResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Endpoint
public class BatchEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

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

		String batchID = request.getBatchID();
		String token = request.getToken();

		// TODO: conexión a la base de datos de Yireth y Andrey através de REST
		// para validar el token, si es valido continua, sino error de autenticación

		// TODO: conexión a la base de datos de Henry, Wilson y Mario através de REST
		// y devolver toda la información del lote seleccionado

		// pseudocodigo en comentarios

//	    if (token es válido) {
//			if (batchID está en la base de datos)
				// estos datos se sacan de la base de datos
				response.setDateCreated(timeFormatter(LocalDateTime.now()));
				response.setFileQuantity(50);
				response.setPath("mulltiplepdf.com/batch1.pdf");
				response.setTimeExpiration(timeSubtract(LocalDateTime.now().minusSeconds(10)));
				response.setState("Vigente");
//			else{
				response.setSuccess("Batch not found");
//			}
//	    } else {
	        // If the user is not authenticated, return an error message
			response.setDateCreated(null);
			response.setFileQuantity(0);
			response.setPath(null);
			response.setTimeExpiration(null);
			response.setState(null);
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
		return days + " days, "+ hours + " hours, " + minutes +" minutes.";

	}


}