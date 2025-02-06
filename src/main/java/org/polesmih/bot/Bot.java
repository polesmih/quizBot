package org.polesmih.bot;

import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.polesmih.bot.settings.ConfigSettings;
import org.polesmih.bot.settings.Counter;
import org.polesmih.bot.settings.FileManager;
import org.polesmih.bot.settings.Sender;
import org.polesmih.command.CommandTypes;
import org.polesmih.db.WriteUser;
import org.polesmih.keyboard.BaseButtonKeyboard;
import org.polesmih.keyboard.inlineGameKeyboards.ArtKeyboard;
import org.polesmih.keyboard.inlineGameKeyboards.FunctionalGameKeyboard;
import org.polesmih.keyboard.inlineGameKeyboards.LegendKeyboard;
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
import static org.polesmih.keyboard.enums.StartButtons.*;


public class Bot extends TelegramLongPollingBot {
    final CommandTypes commandType = new CommandTypes();
    final CommandHandler commandHandler = new CommandHandler();
    private final ConfigSettings settings;
    public static BaseButtonKeyboard baseButtonKeyboard = new BaseButtonKeyboard();
    public final FunctionalGameKeyboard functionalGameKeyboard = new FunctionalGameKeyboard();
    public final ArtKeyboard artKeyboard = new ArtKeyboard();
    public final LegendKeyboard legendKeyboard = new LegendKeyboard();
    public static final String ART_NEXT = "NEXT_PAINT";
    public static final String ART_STATISTIC = "ART_RESULT";
    public static final String LEGEND_NEXT = "NEXT_LEGEND";
    public static final String LEGEND_STATISTIC = "LEGEND_RESULT";
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

            if (commandType.types().contains(messageText)) {
                commandHandler.onUpdateReceived(update);

            } else if (messageText.equals(ART.getButtonType())) {
                WriteUser.artWriteUserIntoDb(LocalDateTime.now().withNano(0), user.getId(), user.getFirstName());
                sendArtQuest(update);

            } else if (messageText.equals(LEGEND.getButtonType())) {
                WriteUser.legendWriteUserIntoDb(LocalDateTime.now().withNano(0), user.getId(), user.getFirstName());
                sendLegendQuest(update);

            } else if (messageText.equals(NETSUKE.getButtonType())) {
                WriteUser.netsukeWriteUserIntoDb(LocalDateTime.now().withNano(0), user.getId(), user.getFirstName());

            } else {
                execute(Sender.sendMessage(chatId, UNKNOWN));
            }

        } else {
            String callData = update.getCallbackQuery().getData();
            long chatId = UpdateUtil.getChatFromUpdate(update).getId();
            long userId = UpdateUtil.getUserFromUpdate(update).getId();

// если callData есть в списке вариантов ответа на вопрос по художникам
            if (ReceiveOptAnsDesc.receiveOptions(update, settings.getJsonArt(),
                    settings.getPathUsersArt(), "q").contains(callData)) {
// если не ответ соответствует ответу по художникам
                if (!callData.equals(ReceiveOptAnsDesc.receiveAnswer(update, settings.getJsonArt(),
                        settings.getPathUsersArt(), "q"))) {

                    FileManager.writeToFile(settings.getPathUsersArt(), "a", userId, "w"
                            + System.lineSeparator());

                    execute(Sender.sendChatAction(chatId, ActionType.TYPING));

                    execute(functionalGameKeyboard.createKeyboard(chatId, FAIL + callData + ELSE,
                            "Следующая картина",
                            ART_NEXT,
                            ART_STATISTIC));

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
                    sendArtQuest(update);
                }

// то же самое по легендам и мифам
            } else if (ReceiveOptAnsDesc.receiveOptions(update, settings.getJsonLegend(),
                    settings.getPathUsersLegend(), "q").contains(callData)) {

                if (!callData.equals(ReceiveOptAnsDesc.receiveAnswer(update, settings.getJsonLegend(),
                        settings.getPathUsersLegend(), "q"))) {

                    FileManager.writeToFile(settings.getPathUsersLegend(), "a", userId, "w"
                            + System.lineSeparator());

                    execute(Sender.sendChatAction(chatId, ActionType.TYPING));

                    execute(functionalGameKeyboard.createKeyboard(chatId, FAIL + callData + ELSE,
                            "Следующий вопрос",
                            LEGEND_NEXT,
                            LEGEND_STATISTIC));

                } else {
                    FileManager.writeToFile(settings.getPathUsersLegend(), "a", userId, 1
                            + System.lineSeparator());

                    execute(Sender.sendChatAction(chatId, ActionType.TYPING));

                    execute(Sender.sendMessage(chatId, WIN +
                            ReceiveOptAnsDesc.receiveDescription(update, settings.getJsonLegend(),
                                    settings.getPathUsersLegend(), "q")));

                    sendLegendQuest(update);
                }


            } else if (callData.equals(ART_NEXT)) {
                sendArtQuest(update);

            } else if (callData.equals(LEGEND_NEXT)) {
                sendLegendQuest(update);

            } else if (callData.equals(ART_STATISTIC)) {
                String statistic = String.format("%.0f", Counter.statistic(settings.getPathUsersArt(), "a", userId));
                execute(Sender.sendChatAction(chatId, ActionType.TYPING));
                execute(Sender.sendMessage(chatId, "Ты угадал " + statistic + " %"));

            } else if (callData.equals(LEGEND_STATISTIC)) {
                String statistic = String.format("%.0f", Counter.statistic(settings.getPathUsersLegend(), "a", userId));
                execute(Sender.sendChatAction(chatId, ActionType.TYPING));
                execute(Sender.sendMessage(chatId, "Ты угадал " + statistic + " %"));

            } else if (callData.equals(TO_MAIN)) {
                execute(baseButtonKeyboard.createKeyboard(chatId));

            } else {
                execute(Sender.sendMessage(chatId, EmojiParser.parseToUnicode(":thinking:")));
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

// если картинка будет долго загружаться - отправка пользователю визуализацию загрузки
        execute(Sender.sendChatAction(chatId, ActionType.UPLOADPHOTO));

// отправляем вопрос-картинку
        execute(Sender.sendPhoto(
                chatId,
                settings.getPathFilesPaint() + gameQuestion));

// прикрепляем к вопросу-картинке клавиатуру с вариантами ответов и функциональными кнопками
        execute(artKeyboard.createKeyboard(chatId,
                randomGameOption1, randomGameOption2, randomGameOption3, ART_STATISTIC));
    }


    // метод формирования и отправки вопроса по легендам и мифам
    @SneakyThrows
    public void sendLegendQuest(Update update) {
        long chatId = UpdateUtil.getChatFromUpdate(update).getId();
        long userId = UpdateUtil.getUserFromUpdate(update).getId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

// выбираем уникальный рандомный объект из json-файла с картинами
        Question newQuestion = GetObject.getUniqueRandomObject(settings.getJsonLegend(),
                settings.getPathUsersLegend(), "q", userId);

//  определяем значение полей объекта
        String gameId = GetFieldValue.getFieldValue(newQuestion, "id");
        String gameQuestion = GetFieldValue.getFieldValue(newQuestion, "question");
        String[] randomArrayGameOptions = GetFieldValue.getRandomArrayOptions(newQuestion, "options");
        String randomGameOption1 = randomArrayGameOptions[0];
        String randomGameOption2 = randomArrayGameOptions[1];
        String randomGameOption3 = randomArrayGameOptions[2];
        String randomGameOption4 = randomArrayGameOptions[3];

// записываем вопрос в файл
        FileManager.writeToFile(settings.getPathUsersLegend(), "q", userId, gameId
                + System.lineSeparator());

// если вопрос будет долго загружаться - отправка пользователю визуализацию печатания
        execute(Sender.sendChatAction(chatId, ActionType.TYPING));

// прикрепляем вопрос и клавиатуру с вариантами ответов и функциональными кнопками
        execute(legendKeyboard.createKeyboard(chatId, gameQuestion,
                randomGameOption1, randomGameOption2, randomGameOption3, randomGameOption4, LEGEND_STATISTIC));
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
