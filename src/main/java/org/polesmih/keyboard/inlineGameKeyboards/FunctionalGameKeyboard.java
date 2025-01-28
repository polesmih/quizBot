package org.polesmih.keyboard.inlineGameKeyboards;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class FunctionalGameKeyboard {

    public SendMessage createKeyboard(long chatId, String messageText, String buttonText,
                                      String textCallBack, String statisticCallBack) {

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(messageText);

// первый ряд кнопок
            InlineKeyboardButton button1 = InlineKeyboardButton
                    .builder()
                    .text(buttonText)
                    .callbackData(textCallBack)
                    .build();
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(button1);

// второй ряд кнопок
            InlineKeyboardButton button2 = InlineKeyboardButton
                    .builder()
                    .text("Статистика")
                    .callbackData(statisticCallBack)
                    .build();

            InlineKeyboardButton button3 = InlineKeyboardButton
                    .builder()
                    .text("На главную")
                    .callbackData("TO_MAIN")
                    .build();

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(button2);
            row2.add(button3);

            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            keyboard.add(row1);
            keyboard.add(row2);

            sendMessage.setReplyMarkup(
                    InlineKeyboardMarkup
                            .builder()
                            .keyboard(keyboard)
                            .build());

            return sendMessage;
    }

}
