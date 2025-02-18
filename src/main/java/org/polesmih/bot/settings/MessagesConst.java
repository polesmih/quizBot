package org.polesmih.bot.settings;

import com.vdurmont.emoji.EmojiParser;

public class MessagesConst {


    public final static String HELLO = "!  " +
            EmojiParser.parseToUnicode(":wink:") +
            "\n\nВ этой игре ты сможешь проверить свои знания в разных областях культуры.  " +
            EmojiParser.parseToUnicode(":sparkles:") +
            "\n\n" + EmojiParser.parseToUnicode(":exclamation:") +
            "  Если что-то пойдет не так или бот перестанет тебе отвечать " +
            "- выбери в меню команду \"запуск бота\" и перезапусти его  " +
            EmojiParser.parseToUnicode(":recycle:");

    public final static String WIN = "<b>Верно!</b> " + EmojiParser.parseToUnicode(":+1:") +
            "\n\nЭто ";

    public final static String ELSE = ".\nПопробуй другой вариант ответа " +
            EmojiParser.parseToUnicode(":point_up:") +
            "\nили выбери действие " +
            EmojiParser.parseToUnicode(":point_down:");

    public final static String FAIL = EmojiParser.parseToUnicode(":x:") +
            "  Не угадал..." +
            "\nЭто не ";

    public final static String NEXT = ".\n\nДля продолжения выбери вариант ответа к последнему вопросу " +
            EmojiParser.parseToUnicode(":point_up:");

    public final static String INSTRUCTION = EmojiParser.parseToUnicode(":small_red_triangle:") +
            "Твоя задача - ответить на вопросы, которые высылает бот. " +
            "Для этого нужно просто выбрать один из предложенных вариантов ответов. " +
            "\n\n" + EmojiParser.parseToUnicode(":small_orange_diamond:") +
            "  Чтобы выбрать другую викторину, посмотреть правила игры или свою статистику " +
            "- выбери соответствующий раздел в меню." +
            "\n\n" + EmojiParser.parseToUnicode(":exclamation:") +
            "  Если что-то пошло не так или бот перестал тебе отвечать " +
            "- выбери в меню команду \"запуск бота\" и перезапусти его  " +
            EmojiParser.parseToUnicode(":recycle:");

    public final static String UNKNOWN = "Неизвестный запрос... " +
            EmojiParser.parseToUnicode(":thinking:");

    public final static String LAW = "<b>Если нужно заняться делами</b>," +
            "\nприглашаем воспользоваться ботом " +
            "\n<a href=\"https://t.me/SimpleForms_bot\">Формы документов</a>";
    public final static String BMY = "\n<b>Если хочешь следить за своим весом</b>, " +
            "приглашаем воспользоваться ботом " +
            "\n<a href=\"https://t.me/BodyMI_bot\">Индекс массы тела</a>";

    public final static String CITY = "\n<b>Если хочешь поиграть в города</b>, " +
            "\nприглашаем воспользоваться ботом " +
            "\n<a href=\"https://t.me/CityQuizGame_bot\">Игра в города</a>";

    public final static String DOG_SHELTER = "<b>Сделай доброе дело</b> - " +
            "помоги Фонду\n<a href=\"https://donate.priut.ru\">Помощь бездомным собакам</a> " +
            EmojiParser.parseToUnicode(":dog:");

}
