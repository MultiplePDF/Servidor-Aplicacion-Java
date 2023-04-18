package com.example.SOAPwebservice;

import io.spring.guides.gs_producing_web_service.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    static final String fakeEmail = "a@gmail.com";
    static final String fakeName = "Angela";
    static final String fakePass = "123";
    static final String fakeToken = "abc123";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerRequest")
    @ResponsePayload
    public RegisterResponse register(@RequestPayload RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();

        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();

        // TODO: conexión a la base de datos de Andrey y Yireth através de REST
        // y comprobar registrar el usuario, si el registro es satisfactorio devuelve el token


        response.setToken(fakeToken);
        // If the user is not authenticated, return an error message
        // response.setToken("An error occurred during registration");


        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) {
        LoginResponse response = new LoginResponse();

        String email = request.getEmail();
        String password = request.getPassword();

        // TODO: conexión a la base de datos de Andrey y Yireth através de REST
        // y comprobar que este usuario esté registrado y las credenciales son correctas

        if (email.equals(fakeEmail) && password.equals(fakePass)) {


            response.setToken(fakeToken);

        } else {
            response.setToken("Invalid username or password");
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "forgotRequest")
    @ResponsePayload
    public ForgotResponse forgotPassword(@RequestPayload ForgotRequest request) {
        ForgotResponse response = new ForgotResponse();

        String email = request.getEmail();

        // TODO: conexión a la base de datos de Andrey y Yireth através de REST
        // y enviar el email para que se proceda con su recuperación de contraseña
        // a través del email retornar si fue exitoso o no el envio del correo

//		pseudo codigo en comentarios:
//		if(rest == email sent)
        response.setSuccessful(true);
//		else
        response.setSuccessful(false);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserDetailsRequest")
    @ResponsePayload
    public GetUserDetailsResponse getUserDetails(@RequestPayload GetUserDetailsRequest request) {
        GetUserDetailsResponse response = new GetUserDetailsResponse();

        String email = request.getEmail();

        // TODO: conexión a la base de datos de Andrey y Yireth através de REST

        response.setEmail(fakeEmail);
        response.setName(fakeName);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "putUserDetailsRequest")
    @ResponsePayload
    public PutUserDetailsResponse putUserDetails(@RequestPayload PutUserDetailsRequest request) {
        PutUserDetailsResponse response = new PutUserDetailsResponse();

        String email = request.getEmail();

        // TODO: conexión a la base de datos de Andrey y Yireth através de REST

        response.setSuccessful("User details succesfully saved");
        return response;
    }

}