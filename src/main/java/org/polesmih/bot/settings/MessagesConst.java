package org.polesmih.bot.settings;

import com.vdurmont.emoji.EmojiParser;

public class MessagesConst {

//    public final static String HELLO = "!  " +
//            EmojiParser.parseToUnicode(":wink:") +
//            "\n\nВ этой игре ты сможешь проверить свои знания в разных областях культуры.";

    public final static String HELLO = "!  " +
            EmojiParser.parseToUnicode(":wink:") +
            "\n\nВ этой игре ты сможешь проверить свои знания в разных областях культуры." +
            "\nПока у нас одна викторина, но со временем тебя ждет много интересного!  " +
            EmojiParser.parseToUnicode(":sparkles:") +
            "\n\n" + EmojiParser.parseToUnicode(":exclamation:") +
            "  Если что-то пойдет не так или бот перестанет тебе отвечать " +
            "- выбери в меню команду \"запуск бота\" и перезапусти его  " +
            EmojiParser.parseToUnicode(":recycle:");

    public final static String WIN = "Верно! " + EmojiParser.parseToUnicode(":+1:") +
            "\n\nЭто ";

    public final static String FAIL = EmojiParser.parseToUnicode(":warning:") +
            "   Не угадал... " +
            "\nПопробуй другой вариант ответа " +
            EmojiParser.parseToUnicode(":point_up:") +
            "\nили выбери действие " +
            EmojiParser.parseToUnicode(":point_down:");

    public final static String INSTRUCTION = EmojiParser.parseToUnicode(":small_red_triangle:") +
            "Твоя задача - ответить на вопросы, которые высылает бот. " +
            "Для этого нужно просто выбрать один из предложенных вариантов ответов. " +
            "\n\n" + EmojiParser.parseToUnicode(":small_orange_diamond:") +
            "  Чтобы выбрать другую викторину или посмотреть правила игры " +
            "- выбери соответствующий раздел в меню." +
            "\n\n" + EmojiParser.parseToUnicode(":exclamation:") +
            "  Если что-то пошло не так или бот перестал тебе отвечать " +
            "- выбери в меню команду \"запуск бота\" и перезапусти его  " +
            EmojiParser.parseToUnicode(":recycle:");

    public final static String UNKNOWN = "Неизвестный запрос... " +
            EmojiParser.parseToUnicode(":thinking:");

    public final static String LAW = "Если нужно заняться делами," +
            "\nприглашаем воспользоваться ботом " +
            "\n<a href=\"https://t.me/SimpleForms_bot\">Формы документов</a>";
    public final static String BMY = "\nЕсли хочешь следить за своим весом, " +
            "приглашаем воспользоваться ботом " +
            "\n<a href=\"https://t.me/BodyMI_bot\">Индекс массы тела</a>";

    public final static String CITY = "\nЕсли хочешь поиграть в города, " +
            "\nприглашаем воспользоваться ботом " +
            "\n<a href=\"https://t.me/CityQuizGame_bot\">Игра в города</a>";

    public final static String DOG_SHELTER = "Сделай доброе дело - " +
            "помоги Фонду\n<a href=\"https://donate.priut.ru\">Помощь бездомным собакам</a> " +
            EmojiParser.parseToUnicode(":dog:");



//    public final static String NEXT = "Что дальше?" +
//            EmojiParser.parseToUnicode(":point_down:");
//
//    public final static String FINAL = EmojiParser.parseToUnicode(":sparkles:") +
//            EmojiParser.parseToUnicode(":sparkles:") +
//            "   Ну вот и все, все вопросы закончились!" + NEXT;

}
