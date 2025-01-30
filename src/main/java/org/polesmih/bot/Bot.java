package org.polesmih.bot;

import lombok.SneakyThrows;
import org.polesmih.bot.settings.ConfigSettings;
import org.polesmih.bot.settings.Sender;
import org.polesmih.command.CommandTypes;
import org.polesmih.db.WriteUser;
import org.polesmih.handler.CommandHandler;
import org.polesmih.keyboard.BaseButtonKeyboard;
import org.polesmih.keyboard.inlineGameKeyboards.ArtKeyboard;
import org.polesmih.keyboard.inlineGameKeyboards.FunctionalGameKeyboard;
import org.polesmih.util.UpdateUtil;
import org.polesmih.util.gameTools.*;
import org.polesmih.util.pojo.Question;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

import static org.polesmih.bot.settings.MessagesConst.*;
import static org.polesmih.command.BotCommands.*;
import static org.polesmih.keyboard.enums.StartButtons.*;


public class Bot extends TelegramLongPollingBot {
    final CommandTypes commandType = new CommandTypes();
    final CommandHandler commandHandler = new CommandHandler();
    private final ConfigSettings settings;
    public static BaseButtonKeyboard baseButtonKeyboard = new BaseButtonKeyboard();
    public final FunctionalGameKeyboard functionalGameKeyboard = new FunctionalGameKeyboard();
    public final ArtKeyboard artKeyboard = new ArtKeyboard();
    public static final String ART_NEXT = "NEXT_PAINT";
    public static final String ART_STATISTIC = "ART_RESULT";
    public static final String TO_MAIN = "TO_MAIN";


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

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());

            if (commandType.types().contains(messageText)) {
                commandHandler.onUpdateReceived(update);

            } else if (messageText.equals(ART.getButtonType())) {
                WriteUser.artWriteUserIntoDb(LocalDateTime.now().withNano(0), user.getId(), user.getFirstName());
                sendArtQuest(update);

            } else if (messageText.equals(LEGEND.getButtonType())) {
                WriteUser.legendWriteUserIntoDb(LocalDateTime.now().withNano(0), user.getId(), user.getFirstName());


            } else if (messageText.equals(NETSUKE.getButtonType())) {
                WriteUser.netsukeWriteUserIntoDb(LocalDateTime.now().withNano(0), user.getId(), user.getFirstName());

            } else {
                execute(Sender.sendMessage(chatId, UNKNOWN));
            }


        } else if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            long chatId = UpdateUtil.getChatFromUpdate(update).getId();
            long userId = UpdateUtil.getUserFromUpdate(update).getId();

            if (callData.equals(
                    ReceiveAnswerAndDescription.receiveAnswer(update, settings.getJsonArt(),
                            settings.getPathUsersArt(), "q"))
            ) {
// записываем в файл маркер ответа - R, если ответ верный
                FileManager.writeToFile(settings.getPathUsersArt(), "a", userId, "R"
                        + System.lineSeparator());

// выдаем пользователю сообщение с результатом и описанием
                execute(Sender.sendMessage(chatId, WIN +
                        ReceiveAnswerAndDescription.receiveDescription(update, settings.getJsonArt(),
                                settings.getPathUsersArt(), "q")));
// и сразу же отправляем новый вопрос
                sendArtQuest(update);

            } else if (callData.equals(ART_NEXT)) {
                sendArtQuest(update);

            } else if (callData.equals(TO_MAIN)) {
                execute(baseButtonKeyboard.createKeyboard(chatId));

            } else if (callData.equals(ART_STATISTIC)) {
                String statistic = String.format("%.2f", Counter.statistic(settings.getPathUsersArt(), "a", userId));
                execute(Sender.sendMessage(chatId, "Ты угадал " + statistic + " %"));

// если ответ неверный, записываем в файл маркер отввета - w
// и выдаем пользователю сообщение с результатом и игровой клавиатурой
            } else {
                FileManager.writeToFile(settings.getPathUsersArt(), "a", userId, "w"
                        + System.lineSeparator());

                execute(functionalGameKeyboard.createKeyboard(chatId, FAIL,
                        "Следующая картина",
                        ART_NEXT,
                        ART_STATISTIC));
            }
        }
    }




// метод формирования и отправки вопроса по художникам
    @SneakyThrows
    public void sendArtQuest(Update update) {

        long chatId = UpdateUtil.getChatFromUpdate(update).getId();
        long userId = UpdateUtil.getUserFromUpdate(update).getId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

// выбираем уникальный рандомный объект из json-файла с картинами
        Question newQuestion = GetObject.getUniqueRandomObject(settings.getJsonArt(),
                settings.getPathUsersArt(), "q", userId);

//  определяем значение полей объекта
        String gameId = GetFieldValue.getFieldValue(newQuestion, "id");
        String gameQuestion = GetFieldValue.getFieldValue(newQuestion, "question");
        String[] randomArrayGameOptions = GetFieldValue.getRandomArrayOptions(newQuestion, "options");
        String randomGameOption1 = randomArrayGameOptions[0];
        String randomGameOption2 = randomArrayGameOptions[1];
        String randomGameOption3 = randomArrayGameOptions[2];

// записываем вопрос в файл
        FileManager.writeToFile(settings.getPathUsersArt(), "q", userId, gameId
                + System.lineSeparator());

// отправляем вопрос-картинку
        execute(Sender.sendPhoto(
                chatId,
                settings.getPathFilesPaint() + gameQuestion));

// прикрепляем к вопросу-картинке клавиатуру с вариантами ответов и функциональными кнопками
        execute(artKeyboard.createKeyboard(chatId,
                randomGameOption1, randomGameOption2, randomGameOption3, ART_STATISTIC));

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
