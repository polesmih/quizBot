package org.polesmih.db;

import java.sql.Date;
import java.time.LocalDateTime;

public class WriteUser {

    static UserModel user = new UserModel();
    public static void artWriteUserIntoDb(LocalDateTime date, Long id, String firstName) {

        user.setDate(date);
        user.setUserTgId(id);
        user.setFirstName(firstName);

        UserModel userModel = new UserModel(date, id, firstName);

        UserConnection.artUserAccounting(userModel);

    }

    public static void legendWriteUserIntoDb(LocalDateTime date, Long id, String firstName) {

        user.setDate(date);
        user.setUserTgId(id);
        user.setFirstName(firstName);

        UserModel userModel = new UserModel(date, id, firstName);

        UserConnection.legendUserAccounting(userModel);

    }

    public static void netsukeWriteUserIntoDb(LocalDateTime date, Long id, String firstName) {

        user.setDate(date);
        user.setUserTgId(id);
        user.setFirstName(firstName);

        UserModel userModel = new UserModel(date, id, firstName);

        UserConnection.netsukeUserAccounting(userModel);

    }


}
