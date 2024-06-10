package com.satc.medify.service;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvLoadService {
    public static void load(String path) {
        try {
            Dotenv dotenv = Dotenv.configure().directory(path).load();
            String dbUrl = dotenv.get("DB_URL");
            String dbUsername = dotenv.get("DB_USERNAME");
            String dbPassword = dotenv.get("DB_PASSWORD");

            if (dbUrl == null || dbUsername == null || dbPassword == null) {
                throw new IllegalArgumentException("Missing required environment variables");
            }

            System.setProperty("spring.datasource.url", dbUrl);
            System.setProperty("spring.datasource.username", dbUsername);
            System.setProperty("spring.datasource.password", dbPassword);
        } catch ( IllegalArgumentException e) {
            System.err.println("Error loading environment variables: " + e.getMessage());
            System.exit(1);
        }
    }
}
