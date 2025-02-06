package org.polesmih.keyboard;

import com.vdurmont.emoji.EmojiParser;
import org.polesmih.keyboard.enums.StartButtons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;


public class BaseButtonKeyboard {

    public SendMessage createKeyboard(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("На появившейся клавиатуре выбери игру " +
                EmojiParser.parseToUnicode(":point_down:"));

        KeyboardRow row1 = new KeyboardRow();
        row1.add(StartButtons.ART.getButtonType());

        KeyboardRow row2 = new KeyboardRow();
        row2.add(StartButtons.LEGEND.getButtonType());

        KeyboardRow row3 = new KeyboardRow();
        row3.add(StartButtons.NETSUKE.getButtonType());

        sendMessage.setReplyMarkup(ReplyKeyboardMarkup.builder()
                .keyboard(Arrays.asList(
                        row1
//                        , row2
//                        , row3
                ))
                .resizeKeyboard(true)
                 .oneTimeKeyboard(true)
                .build());

        return sendMessage;
    }
}
