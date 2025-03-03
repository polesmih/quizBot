package org.polesmih.db;

import lombok.SneakyThrows;
import org.polesmih.db.dbConst.FieldName;
import org.polesmih.db.dbConst.TableName;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserConnection {

    @SneakyThrows
    public static void userAccounting(UserModel userModel) {

        String insert = "INSERT INTO " + TableName.USERS
                + "(" + FieldName.DATE + ", "
                + FieldName.TG_ID + ", "
                + FieldName.NAME + ")"
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
