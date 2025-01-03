package org.polesmih.keyboard.enums;

import com.vdurmont.emoji.EmojiParser;

public enum StartButtons {

    ART("Угадай художника" + EmojiParser.parseToUnicode(":art:")),
    LEGEND("Легенды и мифы Древней Греции" + EmojiParser.parseToUnicode(":classical_building:")),
    NETSUKE ("Нэцке"
            + EmojiParser.parseToUnicode(":shinto_shrine:")
    );

    private final String buttonType;

    StartButtons(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonType() {
        return buttonType;
    }

}
