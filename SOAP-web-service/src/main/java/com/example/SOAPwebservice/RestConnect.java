package com.example.SOAPwebservice;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestConnect {
    public String connect(String route, String get_post, String params) throws IOException {
        try {
            URL url = new URL(route);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(get_post);

            if (get_post.equals("POST") && !params.equals("")) {
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(params);
                out.flush();
                out.close();
            } else if (get_post.equals("GET") && !params.equals("")) {
                con.setRequestProperty("Authorization", params);
            }
            InputStream is;
            if (con.getResponseCode() < 400) {
                is = con.getInputStream();
            } else {
                is = con.getErrorStream();
            }
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(reader);

            String res = "";
            String readed;
            while ((readed = in.readLine()) != null) {
                res += readed + "\n";
            }
            con.disconnect();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}