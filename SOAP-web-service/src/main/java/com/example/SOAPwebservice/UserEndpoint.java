package com.example.SOAPwebservice;

import io.spring.guides.gs_producing_web_service.*;
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerRequest")
    @ResponsePayload
    public RegisterResponse register(@RequestPayload RegisterRequest request) throws IOException, JSONException {
        RegisterResponse response = new RegisterResponse();
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();

        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("confirm_password", confirmPassword);

        String res = Rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/register", "POST", params.toString());
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
            response.setSuccessful(false);
            response.setResponse("There is no response from server");
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
            JSONObject params = new JSONObject();
            params.put("email", email);
            params.put("password", password);
            String res = Rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/login", "POST", params.toString());
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
        } catch (IOException | JSONException e) {
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
    public GetUserDetailsResponse getUserDetails(@RequestPayload GetUserDetailsRequest request) throws IOException {
        GetUserDetailsResponse response = new GetUserDetailsResponse();

        String token = request.getToken();
        String res = Rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/get-user-details", "GET", token);
        try {
            JSONObject jsonObj = new JSONObject(res);
            String name = jsonObj.getString("name");
            String email = jsonObj.getString("email");
            String userSince = jsonObj.getString("created_at");
            String userEdited = jsonObj.getString("update_at");
            response.setName(name);
            response.setEmail(email);
            response.setUserSince(userSince);
            response.setUserEdited(userEdited);
            response.setSuccessful(true);
            response.setResponse("Success");
        } catch (JSONException e) {
            try {
                JSONObject jsonObj = new JSONObject(res);
                String error = jsonObj.getString("error");
                response.setName("");
                response.setEmail("");
                response.setUserSince("");
                response.setUserEdited("");
                response.setSuccessful(false);
                response.setResponse(error);
            } catch (JSONException e2) {
                // in case of an unexpected error
                response.setName("");
                response.setEmail("");
                response.setUserSince("");
                response.setUserEdited("");
                response.setSuccessful(false);
                response.setResponse(String.valueOf(e2));
            }
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editUserDetailsRequest")
    @ResponsePayload
    public EditUserDetailsResponse editUserDetails(@RequestPayload EditUserDetailsRequest request) {
        EditUserDetailsResponse response = new EditUserDetailsResponse();

        // TODO: esperar que el servidor Rest lo tenga listo para implementar la conexión
        String token = request.getToken();

        String newName = request.getName();
        String newEmail = request.getEmail();

        response.setResponse("Detalles del usuario editados correctamente");
        response.setSuccessful(true);
        return response;
    }

}

