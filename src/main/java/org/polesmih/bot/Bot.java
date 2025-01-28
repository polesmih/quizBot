package org.polesmih.bot;

import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.polesmih.bot.settings.ConfigSettings;
import org.polesmih.bot.settings.Sender;
import org.polesmih.command.CommandTypes;
import org.polesmih.db.WriteUser;
import org.polesmih.handler.CommandHandler;
import org.polesmih.handler.GameHandler;
import org.polesmih.util.UpdateUtil;
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

import static org.polesmih.bot.settings.MessagesConst.*;
import static org.polesmih.command.BotCommands.*;
import static org.polesmih.keyboard.enums.StartButtons.*;


public class Bot extends TelegramLongPollingBot {
    final CommandTypes commandType = new CommandTypes();
    final CommandHandler commandHandler = new CommandHandler();
    final GameHandler gameHandler = new GameHandler();
    private final ConfigSettings settings;


    public Bot() {
        this.settings = ConfigSettings.getInstance();
    }

    public void init() throws TelegramApiException {
        execute(new SetMyCommands(LIST_OF_COMMAND, new BotCommandScopeDefault(), null));
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = UpdateUtil.getChatFromUpdate(update).getId();
            User user = UpdateUtil.getUserFromUpdate(update);

            Date date = Date.valueOf(LocalDate.now());

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());


            if (commandType.types().contains(messageText)) {
                commandHandler.onUpdateReceived(update);

            } else if (messageText.equals(ART.getButtonType())) {
                gameHandler.onUpdateReceived(update);
                WriteUser.artWriteUserIntoDb(date, user.getId(), user.getFirstName());


            } else if (messageText.equals(LEGEND.getButtonType())) {

                WriteUser.legendWriteUserIntoDb(date, user.getId(), user.getFirstName());


            } else if (messageText.equals(NETSUKE.getButtonType())) {
                execute(Sender.sendMessage(chatId,
                        "Извините, этот раздел еще в разработке  "
                                + EmojiParser.parseToUnicode(":confused:")));
                WriteUser.netsukeWriteUserIntoDb(date, user.getId(), user.getFirstName());

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
