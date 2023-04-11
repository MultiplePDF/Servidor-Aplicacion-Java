package com.example.SOAPwebservice;

import io.spring.guides.gs_producing_web_service.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FileEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

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
}