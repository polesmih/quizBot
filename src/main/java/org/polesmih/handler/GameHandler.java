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
import static org.polesmih.keyboard.enums.StartButtons.ART;
import static org.polesmih.keyboard.enums.StartButtons.LEGEND;


public class GameHandler extends TelegramLongPollingBot {

    private final static ConfigSettings settings = ConfigSettings.getInstance();
    String messageText;
    SendMessage sendMessage;
    Long chatId;
    Long userId;
//
//
//    // выбрать рандомный объект из json-файла с картинами
//    Question newArtGame = GetObject.getRandomObject(settings.getJsonArt());
//    //  определить значение полей объекта
//    String artGameQuestion = GetFieldValue.getFieldValue(newArtGame, "question");
//    String artGameAnswer = GetFieldValue.getFieldValue(newArtGame, "answer");
//    String artGameDescription = GetFieldValue.getFieldValue(newArtGame, "description");
//    String[] randomArrayArtGameOptions = GetFieldValue.getRandomArrayOptions(newArtGame, "options");
//    String randomArtGameOption1 = randomArrayArtGameOptions[0];
//    String randomArtGameOption2 = randomArrayArtGameOptions[1];
//    String randomArtGameOption3 = randomArrayArtGameOptions[2];
    //

    // выбрать рандомный объект из json-файла с картинами
    Question newArtGame;
    String artGameQuestion;
    String artGameAnswer;
    String artGameDescription;
    String[] randomArrayArtGameOptions;
    String randomArtGameOption1;
    String randomArtGameOption2;
    String randomArtGameOption3;



    // выбрать рандомный объект из json-файла с картинами
    Question newLegendGame = GetObject.getRandomObject(settings.getJsonLegend());
    //  определить значение полей объекта
    String legendGameQuestion = GetFieldValue.getFieldValue(newLegendGame, "question");
    String legendGameAnswer = GetFieldValue.getFieldValue(newLegendGame, "answer");
    String legendGameDescription = GetFieldValue.getFieldValue(newLegendGame, "description");
    String[] randomArrayLegendGameOptions = GetFieldValue.getRandomArrayOptions(newLegendGame, "options");
    String randomLegendGameOption1 = randomArrayLegendGameOptions[0];
    String randomLegendGameOption2 = randomArrayLegendGameOptions[1];
    String randomLegendGameOption3 = randomArrayLegendGameOptions[2];
    String randomLegendGameOption4 = randomArrayLegendGameOptions[3];


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

//        // выбрать рандомный объект из json-файла с картинами
//        Question newArtGame = GetObject.getRandomObject(settings.getJsonArt());
//        //  определить значение полей объекта
//        String artGameQuestion = GetFieldValue.getFieldValue(newArtGame, "question");
//        String artGameAnswer = GetFieldValue.getFieldValue(newArtGame, "answer");
//        String artGameDescription = GetFieldValue.getFieldValue(newArtGame, "description");
//        String[] randomArrayArtGameOptions = GetFieldValue.getRandomArrayOptions(newArtGame, "options");
//        String randomArtGameOption1 = randomArrayArtGameOptions[0];
//        String randomArtGameOption2 = randomArrayArtGameOptions[1];
//        String randomArtGameOption3 = randomArrayArtGameOptions[2];



        if (update.hasMessage() && update.getMessage().hasText()) {

            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();

            sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());

            if (messageText.equals(ART.getButtonType()) || messageText.equals(ART_NEXT.getButtonType())) {
                // выбрать рандомный объект из json-файла с картинами
                newArtGame = GetObject.getRandomObject(settings.getJsonArt());
                //  определить значение полей объекта
                artGameQuestion = GetFieldValue.getFieldValue(newArtGame, "question");
                artGameAnswer = GetFieldValue.getFieldValue(newArtGame, "answer");
                artGameDescription = GetFieldValue.getFieldValue(newArtGame, "description");
                randomArrayArtGameOptions = GetFieldValue.getRandomArrayOptions(newArtGame, "options");
                randomArtGameOption1 = randomArrayArtGameOptions[0];
                randomArtGameOption2 = randomArrayArtGameOptions[1];
                randomArtGameOption3 = randomArrayArtGameOptions[2];

                sendMessage.setText("Кто написал эту картину?");
                sendMessage.setReplyMarkup(GameKeyboard.createFunctionalKeyboard(GameButtons.ART_NEXT.getButtonType(),
                        GameButtons.ART_STATISTIC.getButtonType()));
                execute(sendMessage);

// записываем вопрос в файл
                FileManager.writeToFile(settings.getPathUsersArt(), "q", userId, artGameQuestion
                        + System.lineSeparator());

//            выводим пользователю картинку с вариантами вопросов
                execute(Sender.sendPhoto(
                        chatId,
                        settings.getPathFilesPaint() + artGameQuestion));

                execute(GameKeyboard.createArtInlineGameKeyboard(chatId,
                        randomArtGameOption1, randomArtGameOption2, randomArtGameOption3));
            }

            else if (messageText.equals(LEGEND.getButtonType()) || messageText.equals(LEGEND_NEXT.getButtonType())) {

                sendMessage.setText(legendGameQuestion);
                sendMessage.setReplyMarkup(GameKeyboard.createFunctionalKeyboard(LEGEND_NEXT.getButtonType(),
                        LEGEND_STATISTIC.getButtonType()));
                execute(sendMessage);

                execute(GameKeyboard.createLegendInlineGameKeyboard(chatId,
                        randomLegendGameOption1, randomLegendGameOption2, randomLegendGameOption3, randomLegendGameOption4));
            }
        }



        else if (update.hasCallbackQuery()) {

            String callData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callData.equals(artGameAnswer)) {

// записываем в файл маркер отввета - 1
                FileManager.writeToFile(settings.getPathUsersArt(), "a", userId, "1"
                        + System.lineSeparator()) ;
                execute(Sender.sendMessage(chatId, WIN + artGameDescription + NEXT));

            } else if (callData.equals(legendGameAnswer)) {
                execute(Sender.sendMessage(chatId, WIN + legendGameDescription + NEXT));

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
