package org.polesmih.db;

import java.sql.Date;
import java.time.LocalDateTime;

public class WriteUser {

    static UserModel user = new UserModel();

    public static void writeUserIntoDb(LocalDateTime date, Long id, String name) {

        user.setDate(date);
        user.setUserTgId(id);
        user.setName(name);

        UserModel userModel = new UserModel(date, id, name);

        UserConnection.userAccounting(userModel);

    }


}
