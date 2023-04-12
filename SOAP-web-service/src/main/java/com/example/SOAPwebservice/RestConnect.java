package com.example.SOAPwebservice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class RestConnect {
    public static String connect(String route, String get_post, String params) throws IOException {
        try {
            URL url = new URL(route);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(get_post);
//            System.out.println(con.getResponseCode());

//            con.setRequestProperty("Content-Type", "application/json");
            if (!params.equals("")) {
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(params);
                out.flush();
                out.close();
            }

            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            BufferedReader in = new BufferedReader(reader);
            String readed;
            while ((readed = in.readLine()) != null) {
                System.out.println(readed);
            }
            //BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String output;
            String res = "";
            while ((output = in.readLine()) != null) {
                res += output + "\n";
            }
            con.disconnect();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(connect("http://bd.bucaramanga.upb.edu.co:3000/","GET",""));
        System.out.println(connect("http://bd.bucaramanga.upb.edu.co:3000/lote/uploadLotes","POST","idUsuario=2"));
    }
}
