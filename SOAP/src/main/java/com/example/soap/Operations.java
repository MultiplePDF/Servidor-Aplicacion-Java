package com.example.soap;

public class Operations {
    String signIn(String email, String pass){
        String token = "token";
        return token;
    }
    String signUp(String name, String lastName, String email, String pass){
        String token = "token";
        return token;
    }
    boolean signOut(){
        // clientes deben borrar el token de su storage
        return true;
    }
    boolean forgotPassword(String email){
        return true;
    }

    boolean changePassword(String token, String email, String oldPass, String newPass){
        return true;
    }

    Object loadFilesBase64(String token, Object archivosPaginas){ // JSON en forma de lote (ver clase)
        return null; //retorna un json en forma lote de PDFs
    }

    Object getPastTransactions(String token){
        return null; //retorna un json en forma de lote de PDFs
    }

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
