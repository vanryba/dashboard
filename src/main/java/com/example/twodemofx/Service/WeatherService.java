package com.example.twodemofx.Service;
import com.example.twodemofx.Controllers.AddContentInVBoxController;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherService {

    private static final Logger logger = Logger.getLogger(AddContentInVBoxController.class.getName());

    private static final String ADDRESS
            = "http://api.weatherapi.com/v1/current.json?key=9e09b13ea96a484b94f210531250201&q=Samara&aqi=no";

    public String getTemperature() {
        String temp ="";
        try {
            StringBuilder responseBuilder = getStringBuilder();
            String response = responseBuilder.toString();
            JSONObject jsonResponse = new JSONObject(response);
            temp = String.valueOf(jsonResponse.getJSONObject("current").getDouble("temp_c"));

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при получении погоды", e);
        }
        return temp;
    }

    private static StringBuilder getStringBuilder() throws IOException, URISyntaxException {
        URL url = new URI(ADDRESS).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        Scanner responseScanner = new Scanner(reader);
        StringBuilder responseBuilder = new StringBuilder();
        while (responseScanner.hasNext()) {
            responseBuilder.append(responseScanner.nextLine());
        }
        return responseBuilder;
    }
}
