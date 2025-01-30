package org.polesmih.command.enums;

public enum Commands {

    START ("/start"),
    KEY ("/key"),
    CLEAN ("/clean"),
    RULES ("/rules"),
    INFO ("/info"),
    DONATE ("/donate");


    private final String commandType;


    Commands(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandType() {
        return commandType;
    }


}
