package org.polesmih.keyboard;

import com.vdurmont.emoji.EmojiParser;
import org.polesmih.keyboard.enums.CleanButtons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;

public class CleanButtonKeyboard {


    public SendMessage createKeyboard(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("На появившейся клавиатуре выбери, по какой игре удалить статистику " +
                EmojiParser.parseToUnicode(":point_down:"));

        KeyboardRow row1 = new KeyboardRow();
        row1.add(CleanButtons.CLEAN_ART.getButtonType());

        KeyboardRow row2 = new KeyboardRow();
        row2.add(CleanButtons.CLEAN_LEGEND.getButtonType());

        KeyboardRow row3 = new KeyboardRow();
        row3.add(CleanButtons.CLEAN_POET.getButtonType());

        KeyboardRow row4 = new KeyboardRow();
        row4.add(CleanButtons.CLEAN_NETSUKE.getButtonType());


        sendMessage.setReplyMarkup(ReplyKeyboardMarkup.builder()
                .keyboard(Arrays.asList(
                        row1
//                        , row2
                        , row3
//                        , row4
                ))
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build());

        return sendMessage;
    }

}
