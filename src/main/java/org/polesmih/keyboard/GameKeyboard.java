package org.polesmih.keyboard;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameKeyboard {

    public static ReplyKeyboardMarkup createFunctionalKeyboard(String game) {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(game);
//        row1.add(GameButtons.MAIN.getButtonType());

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row1));
        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);

        return replyKeyboardMarkup;
    }



    public static SendMessage createArtInlineGameKeyboard(long chatId, String opt1, String opt2, String opt3) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выбери вариант ответа");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(opt1);
        inlineKeyboardButton1.setCallbackData(opt1);
        rowInline1.add(inlineKeyboardButton1);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText(opt2);
        inlineKeyboardButton2.setCallbackData(opt2);
        rowInline2.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText(opt3);
        inlineKeyboardButton3.setCallbackData(opt3);
        rowInline3.add(inlineKeyboardButton3);

        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }



    public static SendMessage createLegendInlineGameKeyboard(long chatId, String opt1, String opt2, String opt3, String opt4) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выбери вариант ответа");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(opt1);
        inlineKeyboardButton1.setCallbackData(opt1);
        rowInline1.add(inlineKeyboardButton1);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText(opt2);
        inlineKeyboardButton2.setCallbackData(opt2);
        rowInline2.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText(opt3);
        inlineKeyboardButton3.setCallbackData(opt3);
        rowInline3.add(inlineKeyboardButton3);

        List<InlineKeyboardButton> rowInline4 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText(opt4);
        inlineKeyboardButton4.setCallbackData(opt4);
        rowInline4.add(inlineKeyboardButton4);

        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        rowsInline.add(rowInline4);

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }


}
