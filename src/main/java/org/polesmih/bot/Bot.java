package org.polesmih.bot;

import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.polesmih.bot.settings.ConfigSettings;
import org.polesmih.bot.settings.Sender;
import org.polesmih.command.CommandTypes;
import org.polesmih.db.WriteUser;
import org.polesmih.handler.CommandHandler;
import org.polesmih.handler.GameHandler;
import org.polesmih.keyboard.BaseButtonKeyboard;
import org.polesmih.util.FileManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Date;
import java.time.LocalDate;

import static org.polesmih.bot.settings.MessagesConst.CHOOSE_GAME;
import static org.polesmih.bot.settings.MessagesConst.UNKNOWN;
import static org.polesmih.command.BotCommands.LIST_OF_COMMAND;
import static org.polesmih.keyboard.enums.GameButtons.*;
import static org.polesmih.keyboard.enums.StartButtons.*;


public class Bot extends TelegramLongPollingBot {
    CommandTypes commandType = new CommandTypes();
    CommandHandler commandHandler = new CommandHandler();


    private final static ConfigSettings settings = ConfigSettings.getInstance();
    String messageText;
    Long chatId;
    Message message;
    SendMessage sendMessage;
    User from;
    Date date;
    GameHandler gameHandler;


    public void init() throws TelegramApiException {
        this.execute(new SetMyCommands(LIST_OF_COMMAND, new BotCommandScopeDefault(), null));
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            gameHandler = new GameHandler();

            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();

            message = update.getMessage();
            from = message.getFrom();
            date = Date.valueOf(LocalDate.now());

            sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());


            if (commandType.types().contains(messageText)) {
                commandHandler.onUpdateReceived(update);

            } else if (messageText.equals(MAIN.getButtonType())) {
                sendMessage.setText(CHOOSE_GAME);
                sendMessage.setReplyMarkup(new BaseButtonKeyboard().createBaseStartKeyboard());
                execute(sendMessage);


            } else if (messageText.equals(ART.getButtonType())) {
// когда выбрали викторину Угадай художника - создать файл пользователя
//                FileManager.createFile(settings.getPathArtUsers(), from.getId());

                gameHandler.onUpdateReceived(update);
                WriteUser.artWriteUserIntoDb(date, from.getId(), from.getFirstName());

            } else if (messageText.equals(ART_NEXT.getButtonType())) {
                gameHandler.onUpdateReceived(update);

            } else if (messageText.equals(LEGEND.getButtonType())) {
                gameHandler.onUpdateReceived(update);
                WriteUser.legendWriteUserIntoDb(date, from.getId(), from.getFirstName());

            } else if (messageText.equals(LEGEND_NEXT.getButtonType())) {
                gameHandler.onUpdateReceived(update);

            } else if (messageText.equals(NETSUKE.getButtonType())) {
                execute(Sender.sendMessage(chatId,
                        "Извините, этот раздел еще в разработке  "
                                + EmojiParser.parseToUnicode(":confused:")));
                WriteUser.netsukeWriteUserIntoDb(date, from.getId(), from.getFirstName());

            } else if (messageText.equals(NETSUKE_NEXT.getButtonType())) {
                gameHandler.onUpdateReceived(update);

            } else {
                execute(Sender.sendMessage(chatId, UNKNOWN));
            }


        } else if (update.hasCallbackQuery()) {
            gameHandler.onUpdateReceived(update);
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
