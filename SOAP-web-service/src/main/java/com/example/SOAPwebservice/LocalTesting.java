package com.example.SOAPwebservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LocalTesting {

//    LOCAL TESTING

    public static void main(String[] args) throws IOException, JSONException {

        // TEST CONNECTION TO REST SERVERS
        RestConnect rest = new RestConnect();
        // System.out.println(rest.connect("http://bd.bucaramanga.upb.edu.co:3000/","GET",""));
        String res = rest.connect("http://bd.bucaramanga.upb.edu.co:3000/lote/uploadFiles", "POST", "idLote=6435a9355ffcace83f7cc6e1");

        //  TESTING JSON ARRAY CASTING
//        String res = "[\n" +
//                "    {\n" +
//                "        \"nombreArchivo\": \"arepa\",\n" +
//                "        \"tamanno\": 15.21,\n" +
//                "        \"rutaArchivo\": \"ruta\"\n" +
//                "    }\n" +
//                "]";
        JSONArray jsonArr = new JSONArray(res);
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            System.out.println(jsonObj);
            System.out.println(jsonObj.getString("nombreArchivo"));
            System.out.println(jsonObj.getString("rutaArchivo"));
            System.out.println(jsonObj.getDouble("tamanno"));
        }


    }
}
