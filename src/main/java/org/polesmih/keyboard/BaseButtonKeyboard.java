package org.polesmih.keyboard;

import org.polesmih.keyboard.enums.StartButtons;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;


public class BaseButtonKeyboard {


    public ReplyKeyboardMarkup createBaseStartKeyboard() {

        KeyboardRow row1 = new KeyboardRow();
        row1.add(StartButtons.ART.getButtonType());

        KeyboardRow row2 = new KeyboardRow();
        row2.add(StartButtons.LEGEND.getButtonType());

        KeyboardRow row3 = new KeyboardRow();
        row3.add(StartButtons.NETSUKE.getButtonType());


        return ReplyKeyboardMarkup.builder()
                .keyboard(Arrays.asList(row1
//                        , row2
//                        , row3
                ))
                .resizeKeyboard(true)
                .build();
    }

}
