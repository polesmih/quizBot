package org.polesmih.keyboard.inlineGameKeyboards;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ArtKeyboard {

    public SendMessage createKeyboard(long chatId, String opt1, String opt2, String opt3, String statisticCallBack) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Кто написал эту картину?");

        InlineKeyboardButton button1 = InlineKeyboardButton
                .builder()
                .text(opt1)
                .callbackData(opt1)
                .build();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(button1);

        InlineKeyboardButton button2 = InlineKeyboardButton
                .builder()
                .text(opt2)
                .callbackData(opt2)
                .build();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(button2);

        InlineKeyboardButton button3 = InlineKeyboardButton
                .builder()
                .text(opt3)
                .callbackData(opt3)
                .build();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(button3);

// ряд функциональных кнопок
        InlineKeyboardButton button4 = InlineKeyboardButton
                .builder()
                .text("Статистика")
                .callbackData(statisticCallBack)
                .build();
        InlineKeyboardButton button5 = InlineKeyboardButton
                .builder()
                .text("На главную")
                .callbackData("TO_MAIN")
                .build();
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(button4);
        row4.add(button5);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        sendMessage.setReplyMarkup(
                InlineKeyboardMarkup
                        .builder()
                        .keyboard(keyboard)
                        .build());

        return sendMessage;
    }

}
