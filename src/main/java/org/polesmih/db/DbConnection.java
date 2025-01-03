package org.polesmih.db;

import org.polesmih.bot.settings.ConfigSettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static Connection dbConnection;

    private final static ConfigSettings settings = ConfigSettings.getInstance();

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName(settings.getDbDriver());

        dbConnection = DriverManager.getConnection(settings.getDbSchemaQuiz(),
                settings.getDbRoot(), settings.getDbPassword());

        return dbConnection;
    }


}
