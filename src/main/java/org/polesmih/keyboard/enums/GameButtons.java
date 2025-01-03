package org.polesmih.keyboard.enums;

public enum GameButtons {


//    ART_NEXT ("Следующая картина"),
    ART_NEXT ("Дальше"),
    LEGEND_NEXT("Следующий вопрос"),
    NETSUKE_NEXT("Дальше"),
    MAIN("Выбрать другую викторину");

    private final String buttonType;

    GameButtons(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonType() {
        return buttonType;
    }

}
