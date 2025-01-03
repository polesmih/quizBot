package org.polesmih.db;

import java.sql.Date;

public class WriteUser {

    static UserModel user = new UserModel();
    public static void artWriteUserIntoDb(Date date, Long id, String firstName) {

        user.setDate(date);
        user.setUserTgId(id);
        user.setFirstName(firstName);

        UserModel userModel = new UserModel(date, id, firstName);

        UserConnection.artUserAccounting(userModel);

    }

    public static void legendWriteUserIntoDb(Date date, Long id, String firstName) {

        user.setDate(date);
        user.setUserTgId(id);
        user.setFirstName(firstName);

        UserModel userModel = new UserModel(date, id, firstName);

        UserConnection.legendUserAccounting(userModel);

    }

    public static void netsukeWriteUserIntoDb(Date date, Long id, String firstName) {

        user.setDate(date);
        user.setUserTgId(id);
        user.setFirstName(firstName);

        UserModel userModel = new UserModel(date, id, firstName);

        UserConnection.netsukeUserAccounting(userModel);

    }


}
