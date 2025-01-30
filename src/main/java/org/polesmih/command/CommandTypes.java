package org.polesmih.command;

import java.util.HashSet;
import java.util.Set;

import static org.polesmih.command.enums.Commands.*;


public class CommandTypes {

    public Set<String> types () {

        Set<String> types = new HashSet<String>();
        types.add(START.getCommandType());
        types.add(KEY.getCommandType());
        types.add(CLEAN.getCommandType());
        types.add(RULES.getCommandType());
        types.add(INFO.getCommandType());
        types.add(DONATE.getCommandType());

        return types;

    }


}
