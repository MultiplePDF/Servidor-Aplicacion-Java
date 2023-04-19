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

@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    static final String fakeEmail = "a@gmail.com";
    static final String fakeName = "Angela";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerRequest")
    @ResponsePayload
    public RegisterResponse register(@RequestPayload RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();
        try {
            RestConnect rest = new RestConnect();
            String params = "nombre=" + name +
                    "&email=" + email +
                    "&password=" + password;

            String res = rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/register", "POST", params);
            if (!res.equals("")) {
                try {
                    JSONObject jsonObj = new JSONObject(res);
                    String token = jsonObj.getString("token");
                    response.setToken(token);
                    response.setSuccessful(true);
                    response.setResponse("Success");
                } catch (JSONException e) {
                    try {
                        JSONObject jsonObj = new JSONObject(res);
                        response.setToken("");
                        response.setSuccessful(false);
                        response.setResponse(jsonObj.getString("error"));
                    } catch (JSONException e2) {
                        // in case of an unexpected error
                        response.setToken("");
                        response.setSuccessful(false);
                        response.setResponse(e2.toString());
                    }
                }
            } else {
                response.setToken("");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) {
        LoginResponse response = new LoginResponse();
        String email = request.getEmail();
        String password = request.getPassword();

        try {
            RestConnect rest = new RestConnect();
            String params = "email=" + email +
                    "&password=" + password;
            String res = rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/login", "POST", params);
            if (!res.equals("")) {
                try {
                    JSONObject jsonObj = new JSONObject(res);
                    String token = jsonObj.getString("token");
                    response.setToken(token);
                    response.setSuccessful(true);
                    response.setResponse("Success");
                } catch (JSONException e) {
                    try {
                        JSONObject jsonObj = new JSONObject(res);
                        response.setToken("");
                        response.setSuccessful(false);
                        response.setResponse(jsonObj.getString("error"));
                    } catch (JSONException e2) {
                        // in case of an unexpected error
                        response.setToken("");
                        response.setSuccessful(false);
                        response.setResponse(e2.toString());
                    }
                }
            } else {
                response.setToken("");
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "forgotRequest")
    @ResponsePayload
    public ForgotResponse forgotPassword(@RequestPayload ForgotRequest request) {
        ForgotResponse response = new ForgotResponse();

        String email = request.getEmail();

        // TODO: esperar que el servidor Rest lo tenga listo para implementar la conexión
//		if(rest == email sent)
        response.setSuccessful(true);
        response.setResponse("¡Email enviado!");
//		else
        response.setSuccessful(false);
        response.setResponse("Error al intentar recuperar el contraseña");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserDetailsRequest")
    @ResponsePayload
    public GetUserDetailsResponse getUserDetails(@RequestPayload GetUserDetailsRequest request) {
        GetUserDetailsResponse response = new GetUserDetailsResponse();

        String userID = request.getUserID();

        // TODO: esperar que el servidor Rest lo tenga listo para implementar la conexión

        response.setEmail(fakeEmail);
        response.setName(fakeName);
        response.setResponse("Ocurrió un error al obtener los detalles de usuario");
        response.setSuccessful(false);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editUserDetailsRequest")
    @ResponsePayload
    public EditUserDetailsResponse editUserDetails(@RequestPayload EditUserDetailsRequest request) {
        EditUserDetailsResponse response = new EditUserDetailsResponse();

        // TODO: esperar que el servidor Rest lo tenga listo para implementar la conexión
        String userID = request.getUserID();

        String newName = request.getName();
        String newEmail = request.getEmail();
        String newPass = request.getPassword();

        response.setResponse("Detalles del usuario editados correctamente");
        response.setSuccessful(true);
        return response;
    }
}