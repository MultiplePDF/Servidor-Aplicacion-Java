package com.example.SOAPwebservice;

import javax.naming.AuthenticationException;

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
	public LoginResponse login(@RequestPayload LoginRequest request) throws AuthenticationException {
	    LoginResponse response = new LoginResponse();

	    String username = request.getUsername();
	    String password = request.getPassword();

	    String token = "hola";
	    if (token != null) {
	        response.setToken(token);
	    } else {
	        // If the user is not authenticated, return an error message
	        response.setToken("Invalid username or password");
	    }

	    return response;
	}
}