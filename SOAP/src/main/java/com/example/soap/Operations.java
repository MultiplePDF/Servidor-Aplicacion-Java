package com.example.soap;

import jakarta.servlet.annotation.WebServlet;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.Path;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class Operations {
    @WebMethod
    String signIn(String email, String pass){
        String token = "token";
        return token;
    }
    @WebMethod
    String signUp(String name, String lastName, String email, String pass){
        String token = "token";
        return token;
    }
    @WebMethod
    boolean signOut(){
        // clientes deben borrar el token de su storage
        return true;
    }
    @WebMethod
    boolean forgotPassword(String email){
        return true;
    }
    @WebMethod
    boolean changePassword(String token, String email, String oldPass, String newPass){
        return true;
    }
    @WebMethod
    Object loadFilesBase64(String token, Object archivosPaginas){ // JSON en forma de lote (ver clase)
        return null; //retorna un json en forma lote de PDFs
    }
    @WebMethod
    Object getPastTransactions(String token){
        return null; //retorna un json en forma de lote de PDFs
    }
    @WebMethod
    Object getPastTransactionByID(String token, String idTransaction){
        return null; //retorna un json en forma de lote de PDFs
    }

    // todo: preguntar y discutir en clase
    // falta los de descargar archivo?
    // como se descarga el archivo si lo que devuelven es la ruta en el servidor de archivos?
    // No tiene sentido devolver el archivo pdf serializado para que lo deserailicen los clientes
    // porque entonces para qué es el servidor de archivos?
    // O si?
}
