package org.polesmih.keyboard.enums;

import com.vdurmont.emoji.EmojiParser;

public enum CleanButtons {

    CLEAN_ART("Удалить статистику по \"Угадай художника\""),
    CLEAN_POET("Удалить статистику по \"Угадай поэта\""),
    CLEAN_LEGEND("Удалить статистику по \"Легенды и мифы Древней Греции\""),
    CLEAN_NETSUKE ("Удалить статистику по \"Нэцке\""
    );

    private final String buttonType;

    CleanButtons(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonType() {
        return buttonType;
    }

}
