package com.example.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public class Operations {
    @WebMethod(operationName = "Iniciar sesion")
    public String signIn(String email, String pass){
        String token = "token";
        return token;
    }
    @WebMethod
    public String signUp(String name, String lastName, String email, String pass){
        String token = "token";
        return token;
    }
    @WebMethod
    public boolean signOut(){
        // clientes deben borrar el token de su storage
        return true;
    }
    @WebMethod
    public boolean forgotPassword(String email){
        return true;
    }
    @WebMethod
    public boolean changePassword(String token, String email, String oldPass, String newPass){
        return true;
    }
    @WebMethod
    public Object loadFilesBase64(String token, Object archivosPaginas){ // JSON en forma de lote (ver clase)
        return null; //retorna un json en forma lote de PDFs
    }
    @WebMethod
    public Object getPastTransactions(String token){
        return null; //retorna un json en forma de lote de PDFs
    }
    @WebMethod
    public Object getPastTransactionByID(String token, String idTransaction){
        return null; //retorna un json en forma de lote de PDFs
    }
    public Object getFile(String token, String idLote, String idArchivo){
        return null; //retorna un json en forma de lote de PDFs
    }
    public Object getFiles(String token, String idLote){
        return null; //retorna un json en forma de lote de PDFs
    }

    // todo: preguntar y discutir en clase
    // como se descarga el archivo si lo que devuelven es la ruta en el servidor de archivos?
    // No tiene sentido devolver el archivo pdf serializado para que lo deserializar los clientes
    // porque entonces para qué es el servidor de archivos?
    // O si?
}
