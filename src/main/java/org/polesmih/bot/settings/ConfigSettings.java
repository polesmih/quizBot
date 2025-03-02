package org.polesmih.bot.settings;

import lombok.Data;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class ConfigSettings {
    public static final String FILE_NAME = "config.properties";
    private static ConfigSettings instance = new ConfigSettings();
    private Properties properties;
    private String token;
    private String userName;
    private String dbDriver;
    private String dbSchemaQuiz;
    private String dbRoot;
    private String dbPassword;
    private TelegramBotsApi telegramBotsApi;
    private String pathFilesPaint;
    private String jsonArt;
    private String jsonLegend;
    private String jsonPoets;
    private String pathUsersArt;
    private String pathUsersLegend;
    private String pathUsersPoets;





    public static ConfigSettings getInstance() {
        return instance;
    }

    {
        try {
            properties = new Properties();

            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME)){
                properties.load(inputStream);
            } catch (IOException e) {
                throw new IOException(String.format("Error loading properties file '%s'", FILE_NAME));
            }

            token = properties.getProperty("token");
            if (token == null) {
                throw new RuntimeException("Token value is null");
            }

            userName = properties.getProperty("username");
            if (userName == null) {
                throw new RuntimeException("UserName value is null");
            }

            dbDriver = properties.getProperty("db.driver");
            if (dbDriver == null) {
                throw new RuntimeException("DbDriver value is null");
            }

            dbSchemaQuiz = properties.getProperty("db.schema.quiz");
            if (dbSchemaQuiz == null) {
                throw new RuntimeException("DbSchemaQuiz value is null");
            }

            dbRoot = properties.getProperty("db.root");
            if (dbRoot == null) {
                throw new RuntimeException("DbRoot value is null");
            }

            dbPassword = properties.getProperty("db.password");
            if (dbPassword == null) {
                throw new RuntimeException("DbPassword value is null");
            }

            pathFilesPaint = properties.getProperty("path.files.paint");
            if (pathFilesPaint == null) {
                throw new RuntimeException("Path value is null");
            }

            jsonArt = properties.getProperty("json.art");
            if (jsonArt == null) {
                throw new RuntimeException("Json value is null");
            }

            jsonLegend = properties.getProperty("json.legend");
            if (jsonLegend == null) {
                throw new RuntimeException("Json value is null");
            }

            jsonPoets = properties.getProperty("json.poets");
            if (jsonPoets == null) {
                throw new RuntimeException("Json value is null");
            }

            pathUsersArt = properties.getProperty("path.users.art");
            if (pathUsersArt == null) {
                throw new RuntimeException("Json value is null");
            }

            pathUsersLegend = properties.getProperty("path.users.legend");
            if (pathUsersLegend == null) {
                throw new RuntimeException("Json value is null");
            }

            pathUsersPoets = properties.getProperty("path.users.poets");
            if (pathUsersPoets == null) {
                throw new RuntimeException("Json value is null");
            }


        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("Bot initialisation error: " + e.getMessage());
        }
    }

}