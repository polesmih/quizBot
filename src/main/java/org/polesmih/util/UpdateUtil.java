package org.polesmih.util;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class UpdateUtil {

    public static User getUserFromUpdate(Update update) {
        return update.getMessage() != null ? update.getMessage().getFrom()
                : update.getCallbackQuery().getFrom();
    }

    public static Chat getChatFromUpdate(Update update) {
        return update.getMessage() != null ? update.getMessage().getChat()
                : update.getCallbackQuery().getMessage().getChat();
    }
}
