/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author HuyHoang
 */
public class GoogleUtils {

    private static final String CLIENT_ID = "272169085029-oucd0u94q8i5bcnfrlm4qq2k1r5jdu76.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/PetShopFPT/login-google";

    public static String getToken(String code) throws IOException {
        String url = "https://oauth2.googleapis.com/token";
        String params = "code=" + code
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&grant_type=authorization_code";

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
            out.writeBytes(params);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String input, result = "";
        while ((input = in.readLine()) != null) {
            result += input;
        }
        in.close();

        JsonObject json = JsonParser.parseString(result).getAsJsonObject();
        return json.get("access_token").getAsString();
    }

    public static GoogleUser getUserInfo(String token) throws IOException {
        URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + token);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String input, result = "";
        while ((input = in.readLine()) != null) {
            result += input;
        }
        in.close();

        return new Gson().fromJson(result, GoogleUser.class);
    }
}