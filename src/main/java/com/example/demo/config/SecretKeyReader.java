package com.example.demo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SecretKeyReader {

    public static String getSecretKeyProperty() {

        Properties properties = new Properties();
        try (InputStream is = SecretKeyReader.class.getClassLoader().getResourceAsStream("jwt_secret_key.properties")) {
            if (is == null) {
                throw new IllegalStateException("jwt_secret_key.properties file not found");
            }
            properties.load(is);
            String secretKey = properties.getProperty("secret_key");
            if (secretKey == null || secretKey.isEmpty()) {
                throw new IllegalStateException("API key is missing in jwt_secret_key.properties");
            }
            return secretKey;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Error reading API key from properties file", e);
        }
    }
}
