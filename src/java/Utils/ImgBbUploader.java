/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;

/**
 *
 * @author Lenovo
 */
public class ImgBbUploader {

    private static final String API_KEY = "35ef1f89f9801212152548febdde4f1d";
    private static final String UPLOAD_URL = "https://api.imgbb.com/1/upload";

    private ImgBbUploader() {
    }

    public static String uploadImage(File imageFile) {
        try {
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            URL url = new URL(UPLOAD_URL + "?key=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            String requestBody = "image=" + java.net.URLEncoder.encode(base64Image, "UTF-8");

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestBody.getBytes());
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }

                    try (JsonReader jsonReader = Json.createReader(new StringReader(response.toString()))) {
                        JsonObject jsonResponse = jsonReader.readObject();
                        if (jsonResponse.getBoolean("success")) {
                            return jsonResponse
                                    .getJsonObject("data")
                                    .getString("url");
                        } else {
                            JsonObject error = jsonResponse.getJsonObject("error");
                            System.err.println("ImgBB API Error: " + error.getString("message"));
                        }
                    }
                }
            } else {
                System.err.println("HTTP Error: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
