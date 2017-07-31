package com.example.hp.myjsonpost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by hp on 27-07-2017.
 */

public class ServiceHandler {
    public static final String ERROR = "error";
    private static final String METHOD = "GET";
    private static final int CONN_TIME_OUT = 60000;// milliseconds
    private static final int READ_TIME_OUT = 60000;// milliseconds
    String boundary =  "*****";
    public ServiceHandler(){

    }
    public String getJSON(String url) throws IOException {

        InputStream is = null;
        String contentAsString = ERROR;

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setReadTimeout(CONN_TIME_OUT);
            conn.setConnectTimeout(READ_TIME_OUT);
            conn.setRequestMethod(METHOD);
            conn.setDoInput(true);

            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                Log.d("RESULT OK", "RESULT OK");
                is = conn.getInputStream();
                contentAsString = readIt(is);
                return contentAsString;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }

        }
        return contentAsString;
    }
    private String readIt(InputStream stream) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }
    public String performPostCall(String requestURL,
                                  HashMap<String, String> urlParameters) throws IOException {
        System.out.println("POST url :: " + requestURL);

        URL obj = new URL(requestURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setReadTimeout(READ_TIME_OUT);
        con.setConnectTimeout(CONN_TIME_OUT);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        con.setRequestProperty("Connection", "Keep-Alive");
//        con.setFixedLengthStreamingMode(
//                urlParameters.getBytes().length);
        OutputStreamWriter writer = new OutputStreamWriter(
                con.getOutputStream());

        writer.write(getPostDataString(urlParameters));

        writer.close();
        // For POST only - START
//        OutputStream os = con.getOutputStream();
//        os.write(urlParameters.getBytes());
//        os.flush();
//        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == (HttpURLConnection.HTTP_CREATED) || responseCode == (HttpURLConnection.HTTP_OK)) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
            return response.toString();
            // print result

        } else {

            System.out.println("POST request not worked");
            return "";

        }
    }
    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        Log.d("RESULT PHARMS", " " + result.toString());

        return result.toString();
    }

}
