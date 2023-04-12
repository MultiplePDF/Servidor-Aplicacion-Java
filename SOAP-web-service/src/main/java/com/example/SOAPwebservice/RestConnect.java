package com.example.SOAPwebservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public String connect(String route, String get_post, String params) throws IOException {
        try {
            URL url = new URL(route);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(get_post);

            if (!params.equals("")) {
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(params);
                out.flush();
                out.close();
            }

            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            BufferedReader in = new BufferedReader(reader);

            String res = "";
            String readed;
            while ((readed = in.readLine()) != null) {
                res += readed + "\n";
                // System.out.println(readed);
            }
            con.disconnect();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}