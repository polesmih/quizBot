package org.polesmih.command.enums;

public enum Commands {

    START ("/start"),
    KEY ("/key"),
    RULES ("/rules"),
    INFO ("/info");


    private final String commandType;


    Commands(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandType() {
        return commandType;
    }


}
