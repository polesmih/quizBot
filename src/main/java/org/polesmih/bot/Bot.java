package org.polesmih.bot;

import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.polesmih.bot.settings.ConfigSettings;
import org.polesmih.bot.settings.FileManager;
import org.polesmih.bot.settings.Sender;
import org.polesmih.command.CommandTypes;
import org.polesmih.db.WriteUser;
import org.polesmih.keyboard.inlineGameKeyboards.ThreeOptionsAndPhotoKeyboard;
import org.polesmih.keyboard.inlineGameKeyboards.FunctionalGameKeyboard;
import org.polesmih.keyboard.inlineGameKeyboards.FourOptionsAndTextKeyboard;
import org.polesmih.util.UpdateUtil;
import org.polesmih.util.model.GetFieldValue;
import org.polesmih.util.model.GetObject;
import org.polesmih.util.model.ReceiveOptAnsDesc;
import org.polesmih.util.model.pojo.Question;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

import static org.polesmih.bot.settings.MessagesConst.*;
import static org.polesmih.command.BotCommands.*;
import static org.polesmih.keyboard.enums.CleanButtons.*;
import static org.polesmih.keyboard.enums.StartButtons.*;


public class Bot extends TelegramLongPollingBot {
    final CommandTypes commandType = new CommandTypes();
    final CommandHandler commandHandler = new CommandHandler();
    private final ConfigSettings settings;
    public final FunctionalGameKeyboard functionalGameKeyboard = new FunctionalGameKeyboard();
    public final ThreeOptionsAndPhotoKeyboard artKeyboard = new ThreeOptionsAndPhotoKeyboard();
    public final FourOptionsAndTextKeyboard legendKeyboard = new FourOptionsAndTextKeyboard();
    public static final String ART_NEXT = "NEXT_PAINT";
    public static final String LEGEND_NEXT = "NEXT_LEGEND";
    public static final String POET_NEXT = "NEXT_POET";


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

            if (commandType.types().contains(messageText)) {
                commandHandler.onUpdateReceived(update);

            } else if (messageText.equals(ART.getButtonType())) {
                WriteUser.writeUserIntoDb(LocalDateTime.now().withNano(0), user.getId(),
                        "Художники"
                );
                sendQuestWithThreeOptionsAndPhoto(update,
                        settings.getJsonArt(),
                        settings.getPathUsersArt(),
                        settings.getPathFilesPaint());

            } else if (messageText.equals(LEGEND.getButtonType())) {
                WriteUser.writeUserIntoDb(LocalDateTime.now().withNano(0), user.getId(),
                        "Легенды"
                );
                sendQuestWithFourOptionsAndText(update,
                        settings.getJsonLegend(),
                        settings.getPathUsersLegend());

            } else if (messageText.equals(POET.getButtonType())) {
                WriteUser.writeUserIntoDb(LocalDateTime.now().withNano(0), user.getId(),
                        "Поэты"
                );
                sendQuestWithFourOptionsAndText(update,
                        settings.getJsonPoets(),
                        settings.getPathUsersPoets());

            } else if (messageText.equals(CLEAN_ART.getButtonType())) {
                execute(Sender.sendMessage(chatId, "Статистика ответов по \"Угадай художника\" удалена" + NEXT));
                FileManager.cleanFile(settings.getPathUsersArt(), "a", user.getId());
            } else if (messageText.equals(CLEAN_LEGEND.getButtonType())) {
                execute(Sender.sendMessage(chatId, "Статистика ответов по \"Легенды и мифы Древней Греции\" удалена" + NEXT));
                FileManager.cleanFile(settings.getPathUsersLegend(), "a", user.getId());
            } else if (messageText.equals(CLEAN_POET.getButtonType())) {
                execute(Sender.sendMessage(chatId, "Статистика ответов по \"Угадай поэта\" удалена" + NEXT));
                FileManager.cleanFile(settings.getPathUsersPoets(), "a", user.getId());

            } else {
                execute(Sender.sendMessage(chatId, UNKNOWN));
            }

        } else {
            String callData = update.getCallbackQuery().getData();
            long chatId = UpdateUtil.getChatFromUpdate(update).getId();
            long userId = UpdateUtil.getUserFromUpdate(update).getId();

// если callData есть в списке вариантов ответа на вопрос по художникам
            if (ReceiveOptAnsDesc.receiveOptions(update, settings.getJsonArt(),
                    settings.getPathUsersArt(), "q").contains(callData)
// и callData поступила от соответствующего пользователя
//                    && update.getCallbackQuery().getFrom().getId().equals(userId)
            ) {
// если не ответ соответствует ответу по художникам
                if (!callData.equals(ReceiveOptAnsDesc.receiveAnswer(update, settings.getJsonArt(),
                        settings.getPathUsersArt(), "q"))) {

                    FileManager.writeToFile(settings.getPathUsersArt(), "a", userId, "w"
                            + System.lineSeparator());

                    execute(Sender.sendChatAction(chatId, ActionType.TYPING));

                    execute(functionalGameKeyboard.createKeyboard(chatId, FAIL + callData + ELSE,
                            "Следующая картина", ART_NEXT));

// в противном случае (ответ по художникам верный, записываем в файл маркер отввета - 1
// и выдаем пользователю сообщение с результатом и игровой клавиатурой
                } else {
                    FileManager.writeToFile(settings.getPathUsersArt(), "a", userId, 1
                            + System.lineSeparator());

                    execute(Sender.sendChatAction(chatId, ActionType.TYPING));

// выдаем пользователю сообщение с результатом и описанием
                    execute(Sender.sendMessage(chatId, WIN +
                            ReceiveOptAnsDesc.receiveDescription(update, settings.getJsonArt(),
                                    settings.getPathUsersArt(), "q")));
// и сразу же отправляем новый вопрос по художникам
                    sendQuestWithThreeOptionsAndPhoto(update,
                            settings.getJsonArt(),
                            settings.getPathUsersArt(),
                            settings.getPathFilesPaint());
                }

// то же самое по поэтам
            } else if (ReceiveOptAnsDesc.receiveOptions(update, settings.getJsonPoets(),
                    settings.getPathUsersPoets(), "q").contains(callData)) {

                if (!callData.equals(ReceiveOptAnsDesc.receiveAnswer(update, settings.getJsonPoets(),
                        settings.getPathUsersPoets(), "q"))) {

                    FileManager.writeToFile(settings.getPathUsersPoets(), "a", userId, "w"
                            + System.lineSeparator());

                    execute(Sender.sendChatAction(chatId, ActionType.TYPING));

                    execute(functionalGameKeyboard.createKeyboard(chatId, FAIL + callData + ELSE,
                            "Следующий вопрос", POET_NEXT));

                } else {
                    FileManager.writeToFile(settings.getPathUsersPoets(), "a", userId, 1
                            + System.lineSeparator());

                    execute(Sender.sendChatAction(chatId, ActionType.TYPING));

                    execute(Sender.sendMessage(chatId, WIN +
                            ReceiveOptAnsDesc.receiveDescription(update, settings.getJsonPoets(),
                                    settings.getPathUsersPoets(), "q")));

                    sendQuestWithFourOptionsAndText(update,
                            settings.getJsonPoets(),
                            settings.getPathUsersPoets());
                }


//// то же самое по легендам и мифам
//            } else if (ReceiveOptAnsDesc.receiveOptions(update, settings.getJsonLegend(),
//                    settings.getPathUsersLegend(), "q").contains(callData)) {
//
//                if (!callData.equals(ReceiveOptAnsDesc.receiveAnswer(update, settings.getJsonLegend(),
//                        settings.getPathUsersLegend(), "q"))) {
//
//                    FileManager.writeToFile(settings.getPathUsersLegend(), "a", userId, "w"
//                            + System.lineSeparator());
//
//                    execute(Sender.sendChatAction(chatId, ActionType.TYPING));
//
//                    execute(functionalGameKeyboard.createKeyboard(chatId, FAIL + callData + ELSE,
//                            "Следующий вопрос", LEGEND_NEXT));
//
//                } else {
//                    FileManager.writeToFile(settings.getPathUsersLegend(), "a", userId, 1
//                            + System.lineSeparator());
//
//                    execute(Sender.sendChatAction(chatId, ActionType.TYPING));
//
//                    execute(Sender.sendMessage(chatId, WIN +
//                            ReceiveOptAnsDesc.receiveDescription(update, settings.getJsonLegend(),
//                                    settings.getPathUsersLegend(), "q")));
//
//                    sendLegendQuest(update);
//                }


            } else if (callData.equals(ART_NEXT)) {
                sendQuestWithThreeOptionsAndPhoto(update,
                        settings.getJsonArt(),
                        settings.getPathUsersArt(),
                        settings.getPathFilesPaint());

            } else if (callData.equals(POET_NEXT)) {
                sendQuestWithFourOptionsAndText(update,
                        settings.getJsonPoets(),
                        settings.getPathUsersPoets());

            } else if (callData.equals(LEGEND_NEXT)) {
                sendQuestWithFourOptionsAndText(update,
                        settings.getJsonLegend(),
                        settings.getPathUsersLegend());

            } else {
                execute(Sender.sendMessage(chatId, EmojiParser.parseToUnicode(":thinking:")));
            }
        }
    }


    // метод формирования и отправки вопроса, где 3 варианта ответа и картинка
    @SneakyThrows
    public void sendQuestWithThreeOptionsAndPhoto(Update update, String jsonPath, String pathUserFile, String paintPath) {
        long chatId = UpdateUtil.getChatFromUpdate(update).getId();
        long userId = UpdateUtil.getUserFromUpdate(update).getId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

// выбираем уникальный рандомный объект из json-файла
        Question newQuestion = GetObject.getUniqueRandomObject(jsonPath,
                pathUserFile, "q", userId);

//  определяем значение полей объекта
        String gameId = GetFieldValue.getFieldValue(newQuestion, "id");
        String gameQuestion = GetFieldValue.getFieldValue(newQuestion, "question");
        String[] randomArrayGameOptions = GetFieldValue.getRandomArrayOptions(newQuestion, "options");
        String randomGameOption1 = randomArrayGameOptions[0];
        String randomGameOption2 = randomArrayGameOptions[1];
        String randomGameOption3 = randomArrayGameOptions[2];

// записываем вопрос в файл
        FileManager.writeToFile(pathUserFile, "q", userId, gameId
                + System.lineSeparator());

// если картинка будет долго загружаться - отправка пользователю визуализацию загрузки
        execute(Sender.sendChatAction(chatId, ActionType.UPLOADPHOTO));

// отправляем вопрос-картинку
        execute(Sender.sendPhoto(
                chatId,
                paintPath + gameQuestion));

// прикрепляем к вопросу-картинке клавиатуру с вариантами ответов и функциональными кнопками
        execute(artKeyboard.createKeyboard(chatId,
                randomGameOption1, randomGameOption2, randomGameOption3));
    }


    // метод формирования и отправки вопроса, где 4 варианта ответа и текст
    @SneakyThrows
    public void sendQuestWithFourOptionsAndText(Update update, String jsonPath, String pathUserFile) {
        long chatId = UpdateUtil.getChatFromUpdate(update).getId();
        long userId = UpdateUtil.getUserFromUpdate(update).getId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

// выбираем уникальный рандомный объект из json-файла
        Question newQuestion = GetObject.getUniqueRandomObject(jsonPath,
                pathUserFile, "q", userId);

//  определяем значение полей объекта
        String gameId = GetFieldValue.getFieldValue(newQuestion, "id");
        String gameQuestion = GetFieldValue.getFieldValue(newQuestion, "question");
        String[] randomArrayGameOptions = GetFieldValue.getRandomArrayOptions(newQuestion, "options");
        String randomGameOption1 = randomArrayGameOptions[0];
        String randomGameOption2 = randomArrayGameOptions[1];
        String randomGameOption3 = randomArrayGameOptions[2];
        String randomGameOption4 = randomArrayGameOptions[3];

// записываем вопрос в файл
        FileManager.writeToFile(pathUserFile, "q", userId, gameId
                + System.lineSeparator());

// если вопрос будет долго загружаться - отправка пользователю визуализацию печатания
        execute(Sender.sendChatAction(chatId, ActionType.TYPING));

// прикрепляем вопрос и клавиатуру с вариантами ответов и функциональными кнопками
        execute(legendKeyboard.createKeyboard(chatId, gameQuestion,
                randomGameOption1, randomGameOption2, randomGameOption3, randomGameOption4));
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
