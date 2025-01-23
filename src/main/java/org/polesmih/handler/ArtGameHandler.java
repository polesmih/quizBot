package org.polesmih.handler;

import lombok.SneakyThrows;
import org.polesmih.bot.settings.ConfigSettings;
import org.polesmih.bot.settings.Sender;
import org.polesmih.keyboard.GameKeyboard;
import org.polesmih.keyboard.enums.GameButtons;
import org.polesmih.util.gameTools.FileManager;
import org.polesmih.util.gameTools.GetFieldValue;
import org.polesmih.util.gameTools.GetObject;
import org.polesmih.util.pojo.Question;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.polesmih.bot.settings.MessagesConst.*;
import static org.polesmih.keyboard.enums.GameButtons.*;
import static org.polesmih.keyboard.enums.StartButtons.*;

public class ArtGameHandler extends TelegramLongPollingBot {

    private final static ConfigSettings settings = ConfigSettings.getInstance();
    String messageText;
    SendMessage sendMessage;
    Long chatId;
    Long userId;
    Question newGame;
    String gameId;
    String gameQuestion;
    String gameAnswer;
    String gameDescription;
    String[] randomArrayGameOptions;
    String randomGameOption1;
    String randomGameOption2;
    String randomGameOption3;


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();

            sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());

            if (messageText.equals(ART.getButtonType()) || messageText.equals(ART_NEXT.getButtonType())) {
// выбираем уникальный рандомный объект из json-файла с картинами
                newGame = GetObject.getUniqueRandomObject(settings.getJsonArt(),
                        settings.getPathUsersArt(), "q", userId);

//  определяем значение полей объекта
                gameId = GetFieldValue.getFieldValue(newGame, "id");
                gameQuestion = GetFieldValue.getFieldValue(newGame, "question");
                gameAnswer = GetFieldValue.getFieldValue(newGame, "answer");
                gameDescription = GetFieldValue.getFieldValue(newGame, "description");
                randomArrayGameOptions = GetFieldValue.getRandomArrayOptions(newGame, "options");
                randomGameOption1 = randomArrayGameOptions[0];
                randomGameOption2 = randomArrayGameOptions[1];
                randomGameOption3 = randomArrayGameOptions[2];

                sendMessage.setText("Кто написал эту картину?");
                sendMessage.setReplyMarkup(GameKeyboard.createFunctionalKeyboard(GameButtons.ART_NEXT.getButtonType(),
                        GameButtons.ART_STATISTIC.getButtonType()));
                execute(sendMessage);

// записываем вопрос в файл
                FileManager.writeToFile(settings.getPathUsersArt(), "q", userId, gameId
                        + System.lineSeparator());

// выводим пользователю картинку с вариантами вопросов
                execute(Sender.sendPhoto(
                        chatId,
                        settings.getPathFilesPaint() + gameQuestion));

                execute(GameKeyboard.createArtInlineGameKeyboard(chatId,
                        randomGameOption1, randomGameOption2, randomGameOption3));
            }
        }


        else if (update.hasCallbackQuery()) {

            String callData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();


// определяем правильный ответ и описание ответа на основании последнего id в файле вопросов пользователя

// если ответ верный
            if (callData.equals(gameAnswer)) {

// записываем в файл маркер отввета - 1
                FileManager.writeToFile(settings.getPathUsersArt(), "a", userId, "1"
                        + System.lineSeparator()) ;
                execute(Sender.sendMessage(chatId, WIN + gameDescription + NEXT));

// если ответ верный
            } else {
// записываем в файл маркер отввета - 0
                FileManager.writeToFile(settings.getPathUsersArt(), "a", userId, "0"
                        + System.lineSeparator());
                execute(Sender.sendMessage(chatId, FAIL));
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
