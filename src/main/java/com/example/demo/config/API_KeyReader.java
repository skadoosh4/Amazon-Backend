package com.example.demo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class API_KeyReader {

    public static String getApiKey() {
        Properties properties = new Properties();
        try (InputStream is = API_KeyReader.class.getClassLoader().getResourceAsStream("api_key.properties")) {
            if (is == null) {
                throw new IllegalStateException("api_key.properties file not found");
            }
            properties.load(is);
            String apiKey = properties.getProperty("PROFANITY_FILTER_API_KEY");
            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalStateException("API key is missing in api_key.properties");
            }
            return apiKey;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Error reading API key from properties file", e);
        }
    }
}
