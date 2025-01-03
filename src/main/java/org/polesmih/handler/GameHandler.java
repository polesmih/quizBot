package org.polesmih.handler;

import lombok.SneakyThrows;
import org.polesmih.bot.settings.ConfigSettings;
import org.polesmih.bot.settings.Sender;
import org.polesmih.keyboard.GameKeyboard;
import org.polesmih.keyboard.enums.GameButtons;
import org.polesmih.util.FileManager;
import org.polesmih.util.GetFieldValue;
import org.polesmih.util.GetObject;
import org.polesmih.util.pojo.Question;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.polesmih.bot.settings.MessagesConst.*;
import static org.polesmih.keyboard.enums.GameButtons.ART_NEXT;
import static org.polesmih.keyboard.enums.GameButtons.LEGEND_NEXT;
import static org.polesmih.keyboard.enums.StartButtons.ART;
import static org.polesmih.keyboard.enums.StartButtons.LEGEND;


public class GameHandler extends TelegramLongPollingBot {

    private final static ConfigSettings settings = ConfigSettings.getInstance();
    String messageText;
    Message message;
    SendMessage sendMessage;
    User from;
    Long chatId;


    // выбрать рандомный объект из json-файла с картинами
    Question newArtGame = GetObject.getRandomObject(settings.getJsonArt());

    //  определить значение полей объекта
    String artGameQuestion = GetFieldValue.getFieldValue(newArtGame, "question");
    String artGameAnswer = GetFieldValue.getFieldValue(newArtGame, "answer");
    String artGameDescription = GetFieldValue.getFieldValue(newArtGame, "description");
    String[] randomArrayArtGameOptions = GetFieldValue.getRandomArrayOptions(newArtGame, "options");
    String randomArtGameOption1 = randomArrayArtGameOptions[0];
    String randomArtGameOption2 = randomArrayArtGameOptions[1];
    String randomArtGameOption3 = randomArrayArtGameOptions[2];



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

        if (update.hasMessage() && update.getMessage().hasText()) {

            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();

            message = update.getMessage();
            from = message.getFrom();

            sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());

            if (messageText.equals(ART.getButtonType()) || messageText.equals(ART_NEXT.getButtonType())) {

// записать вопрос в файл пользователя с новой строки
                FileManager.writeToFile(settings.getPathArtUsers(), from.getId(),
                        artGameQuestion + System.lineSeparator());

                sendMessage.setText("Кто написал эту картину?");
                sendMessage.setReplyMarkup(GameKeyboard.createFunctionalKeyboard(GameButtons.ART_NEXT.getButtonType()));
                execute(sendMessage);

//            выводим пользователю картинку с вариантами вопросов
                execute(Sender.sendPhoto(
                        chatId,
                        settings.getPathFilesPaint() + artGameQuestion));

                execute(GameKeyboard.createArtInlineGameKeyboard(chatId,
                        randomArtGameOption1, randomArtGameOption2, randomArtGameOption3));


//                GetObject.getUniqueRandomObject(settings.getJsonArt(),
//                        settings.getPathArtUsers() + from.getId() + ".txt", from.getId());


            }

            else if (messageText.equals(LEGEND.getButtonType()) || messageText.equals(LEGEND_NEXT.getButtonType())) {

                sendMessage.setText(legendGameQuestion);
                sendMessage.setReplyMarkup(GameKeyboard.createFunctionalKeyboard(LEGEND_NEXT.getButtonType()));
                execute(sendMessage);

                execute(GameKeyboard.createLegendInlineGameKeyboard(chatId,
                        randomLegendGameOption1, randomLegendGameOption2, randomLegendGameOption3, randomLegendGameOption4));
            }
        }



        else if (update.hasCallbackQuery()) {

            String callData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callData.equals(artGameAnswer)) {
//// записать ответ в файл пользователя загдавными буквами с новой строки
//                FileManager.writeToFile(settings.getPathArtUsers(), from.getId(),
//                        artGameAnswer.toUpperCase() + System.lineSeparator());

                execute(Sender.sendMessage(chatId, WIN + artGameDescription + NEXT));



            } else if (callData.equals(legendGameAnswer)) {
                execute(Sender.sendMessage(chatId, WIN + legendGameDescription + NEXT));

            } else {
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
