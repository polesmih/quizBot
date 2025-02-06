package org.polesmih.bot;

import lombok.SneakyThrows;
import org.polesmih.bot.settings.ConfigSettings;
import org.polesmih.bot.settings.Sender;
import org.polesmih.keyboard.BaseButtonKeyboard;
import org.polesmih.util.UpdateUtil;
import org.polesmih.bot.settings.FileManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.polesmih.bot.settings.MessagesConst.*;
import static org.polesmih.command.enums.Commands.*;

public class CommandHandler extends TelegramLongPollingBot {
    private final static ConfigSettings settings = ConfigSettings.getInstance();
    final BaseButtonKeyboard baseButtonKeyboard = new BaseButtonKeyboard();


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        long chatId = UpdateUtil.getChatFromUpdate(update).getId();
        User user = UpdateUtil.getUserFromUpdate(update);
        String messageText = update.getMessage().getText();

        if (update.hasMessage() && update.getMessage().hasText()) {

                if (messageText.equals(START.getCommandType())) {
                    execute(Sender.sendMessage(chatId, "Привет, " + user.getFirstName() + HELLO));
                    execute(baseButtonKeyboard.createKeyboard(chatId));

// создаем сразу при старте пустые файлы пользователя для записи индикаторов вопросов
                    FileManager.writeToFile(settings.getPathUsersArt(), "q", user.getId(), "");
                    FileManager.writeToFile(settings.getPathUsersLegend(), "q", user.getId(), "");
                    FileManager.writeToFile(settings.getPathUsersNetsuke(), "q", user.getId(), "");

                } else if (messageText.equals(KEY.getCommandType())) {
                    execute(baseButtonKeyboard.createKeyboard(chatId));

                } else if (messageText.equals(CLEAN.getCommandType())) {
                    execute(Sender.sendMessage(chatId, "Статистика ответов по всем играм удалена"));
                    FileManager.cleanFile(settings.getPathUsersArt(), "a", user.getId());
                    FileManager.cleanFile(settings.getPathUsersLegend(), "a", user.getId());
                    FileManager.cleanFile(settings.getPathUsersNetsuke(), "a", user.getId());

                } else if (messageText.equals(RULES.getCommandType())) {
                    execute(Sender.sendMessage(chatId, INSTRUCTION));

                } else if (messageText.equals(INFO.getCommandType())) {
                    execute(Sender.sendMessage(chatId, CITY));
                    execute(Sender.sendMessage(chatId, LAW));
                    execute(Sender.sendMessage(chatId, BMY));

                } else if (messageText.equals(DONATE.getCommandType())) {
                    execute(Sender.sendMessage(chatId, DOG_SHELTER));
                }
            }
    }


    @Override
    public String getBotUsername() {
        return settings.getUserName();
    }

    @Override
    public String getBotToken() {
        return settings.getToken();
    }

}
