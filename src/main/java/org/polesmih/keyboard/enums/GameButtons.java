package org.polesmih.keyboard.enums;

import com.vdurmont.emoji.EmojiParser;

public enum GameButtons {


    MAIN("На главную страницу"),
    ART_NEXT ("Следующая картина"),
    ART_STATISTIC ("Доля угаданных художников"),
    LEGEND_NEXT("Следующий вопрос"),
    LEGEND_STATISTIC ("Доля угаданных вопросов по легендам и мифам"),
    NETSUKE_NEXT("Дальше"),
    NETSUKE_STATISTIC ("Доля угаданных вопросов по нэцке");


    private final String buttonType;

    GameButtons(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonType() {
        return buttonType;
    }

}
