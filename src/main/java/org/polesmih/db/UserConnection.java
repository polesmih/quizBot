package org.polesmih.db;

import lombok.SneakyThrows;
import org.polesmih.db.dbConst.FieldName;
import org.polesmih.db.dbConst.TableName;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserConnection {

    @SneakyThrows
    public static void artUserAccounting(UserModel userModel) {

        String insert = "INSERT INTO " + TableName.ART_VISITS
                + "(" + FieldName.DATE + ", "
                + FieldName.TG_ID + ", "
                + FieldName.FIRST_NAME + ")"
                + "VALUES (?, ?, ?)";


        try {

            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(insert);
            preparedStatement.setString(1, String.valueOf(userModel.getDate()));
            preparedStatement.setString(2, String.valueOf(userModel.getUserTgId()));
            preparedStatement.setString(3, userModel.getFirstName());

            preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    public static void legendUserAccounting(UserModel userModel) {

        String insert = "INSERT INTO " + TableName.LEGEND_VISITS
                + "(" + FieldName.DATE + ", "
                + FieldName.TG_ID + ", "
                + FieldName.FIRST_NAME + ")"
                + "VALUES (?, ?, ?)";

        try {

            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(insert);
            preparedStatement.setString(1, String.valueOf(userModel.getDate()));
            preparedStatement.setString(2, String.valueOf(userModel.getUserTgId()));
            preparedStatement.setString(3, userModel.getFirstName());

            preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    public static void poetsUserAccounting(UserModel userModel) {

        String insert = "INSERT INTO " + TableName.POET_VISITS
                + "(" + FieldName.DATE + ", "
                + FieldName.TG_ID + ", "
                + FieldName.FIRST_NAME + ")"
                + "VALUES (?, ?, ?)";

        try {

            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(insert);
            preparedStatement.setString(1, String.valueOf(userModel.getDate()));
            preparedStatement.setString(2, String.valueOf(userModel.getUserTgId()));
            preparedStatement.setString(3, userModel.getFirstName());

            preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
