package com.gopang.paymentserver.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class TokenService {

//    @Value("${portone.api.key}")
//    private String imp_key;
//
//    @Value("${portone.api.secret}")
//    private String imp_secret;

    public String getToken() throws IOException {

        String imp_key = "7682212651011477";
        String imp_secret = "qDS95VMAqDI7N6e8olBhbnsFvZ4vfc6HnvTPsQyYcQareVHFl6suL2CCyKnty1wY6jMFZpAauK33n1q1";

        URL url = new URL("https://api.iamport.kr/users/getToken");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.addProperty("imp_key", imp_key);
        json.addProperty("imp_secret", imp_secret);
//        System.out.println(imp_key + "\n"+ imp_secret);

        try (OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream())) {
            osw.write(json.toString());
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
                String access_token = jsonResponse.getAsJsonObject("response").get("access_token").getAsString();
                conn.disconnect();
                return access_token;
            }
        } else {
            throw new IOException("HTTP 요청 실패 : " + responseCode);
        }
    }
}