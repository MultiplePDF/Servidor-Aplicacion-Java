package com.example.SOAPwebservice;

import javax.naming.AuthenticationException;

import io.spring.guides.gs_producing_web_service.RegisterRequest;
import io.spring.guides.gs_producing_web_service.RegisterResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.spring.guides.gs_producing_web_service.LoginRequest;
import io.spring.guides.gs_producing_web_service.LoginResponse;

@Endpoint
public class ClientEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
	@ResponsePayload
	public LoginResponse login(@RequestPayload LoginRequest request) {
	    LoginResponse response = new LoginResponse();

	    String email = request.getEmail();
	    String password = request.getPassword();

		// TODO: conexión a la base de datos de Andrey y Yireth através de REST
		// y comprobar que este usuario esté registrado y las credenciales son correctas

	    String token = "hola login";
	    if (token != null) {
	        response.setToken(token);
	    } else {
	        // If the user is not authenticated, return an error message
	        response.setToken("Invalid username or password");
	    }

	    return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerRequest")
	@ResponsePayload
	public RegisterResponse register(@RequestPayload RegisterRequest request) {
		RegisterResponse response = new RegisterResponse();

		String name = request.getName();
		String email = request.getEmail();
		String password = request.getPassword();

		// TODO: conexión a la base de datos de Andrey y Yireth através de REST
		// y comprobar registrar el usuario, si el registro es satisfactorio devuelve el token

		String token = "hola register";
		if (token != null) {
			response.setToken(token);
		} else {
			// If the user is not authenticated, return an error message
			response.setToken("An error occurred during registration");
		}

		return response;
	}
}