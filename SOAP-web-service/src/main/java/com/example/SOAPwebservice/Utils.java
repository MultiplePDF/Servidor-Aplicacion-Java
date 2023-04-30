package com.example.SOAPwebservice;

import java.io.*;
import java.io.File;
import java.security.MessageDigest;
import java.util.Base64;

public class Utils {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(
                new File("C:\\Users\\Angela\\Downloads\\Notas Spyder.docx"));
        byte[] bytes = new byte[(int) fis.available()];
        fis.read(bytes);
        String base64String = new String(Base64.getEncoder().encode(bytes));
        System.out.println(base64String);
        System.out.println();
        String checksum1 = "";

        try {
            checksum1 = obtenerMD5ComoString("C:\\Users\\Angela\\Downloads\\Notas Spyder.docx");
//	            System.out.println("El MD5 checksum de " + "C:\\Users\\admin\\Pictures\\PRUEBA\\EJERCICIO APLICACIÓN CONTABLE - ENERO 2022.pptx" + " es " + checksum1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(checksum1);
    }


    public static byte[] obtenerChecksum(String nombreArchivo) throws Exception {
        InputStream fis = new FileInputStream(nombreArchivo);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("SHA-256");
        int numRead;
        // Leer el archivo pedazo por pedazo
        do {
            // Leer datos y ponerlos dentro del búfer
            numRead = fis.read(buffer);
            // Si se leyó algo, se actualiza el MessageDigest
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        // Devolver el arreglo de bytes
        return complete.digest();
    }

    public static String obtenerMD5ComoString(String nombreArchivo) throws Exception {
        // Convertir el arreglo de bytes a cadena
        byte[] b = obtenerChecksum(nombreArchivo);
        StringBuilder resultado = new StringBuilder();

        for (byte unByte : b) {
            resultado.append(Integer.toString((unByte & 0xff) + 0x100, 16).substring(1));
        }
        return resultado.toString();
    }

}
