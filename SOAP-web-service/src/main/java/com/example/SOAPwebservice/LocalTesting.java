package com.example.SOAPwebservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LocalTesting {

//    LOCAL TESTING

    public static void main(String[] args) throws IOException, JSONException {

        // TEST CONNECTION TO REST SERVERS
        RestConnect rest = new RestConnect();
//        System.out.println(rest.connect("http://bd.bucaramanga.upb.edu.co:3000/","GET",""));
//        String res = rest.connect("http://bd.bucaramanga.upb.edu.co:3000/lote/uploadLotes", "POST", "idUsuario=2");
//        System.out.println(res);

        JSONObject cred = new JSONObject();
        cred.put("name", "Angela");
        cred.put("lastname", "Remolina");
        cred.put("email", "angela@gmail.com");
        cred.put("password", "123456789");
        cred.put("confirm_password", "123456789");

        String res = rest.connect("http://autenticacion.bucaramanga.upb.edu.co:4000/auth/register", "POST", cred.toString());
        System.out.println(res);


        //  TESTING JSON ARRAY CASTING
//        String res = "[\n" +
//                "    {\n" +
//                "        \"nombreArchivo\": \"arepa\",\n" +
//                "        \"tamanno\": 15.21,\n" +
//                "        \"rutaArchivo\": \"ruta\"\n" +
//                "    }\n" +
//                "]";
//        JSONArray jsonArr = new JSONArray(res);
//        for (int i = 0; i < jsonArr.length(); i++) {
//            JSONObject jsonObj = jsonArr.getJSONObject(i);
//            System.out.println(jsonObj);
//            System.out.println(jsonObj.getString("createdAt"));
//            System.out.println(jsonObj.getInt("numeroArchivos"));
//            System.out.println(jsonObj.getString("vigencia"));
//        }
//
//        ArrayList<File> archivos1List = new ArrayList<>();
//        archivos1List.add(new File("a","sss","hola"));
//        archivos1List.add(new File("a","qqq","chao"));
//        archivos1List.add(new File("a","zzz","bueno"));
//        archivos1List.add(new File("a","ccc","malo"));
//        File[] archivos1 = archivos1List.toArray(new File[archivos1List.size()]);
//        for (Archivo x:archivos1) {
//            System.out.println(x.toString());
//        }
//
//        SubBatch b = new SubBatch("a","2",archivos1);
//        System.out.println(b.toString());
//        JSONObject a = new JSONObject(b.toString());
//        System.out.println(a);

    }
}
