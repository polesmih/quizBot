package org.polesmih.command;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

import static org.polesmih.command.enums.Commands.*;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMAND = List.of(
            new BotCommand(START.getCommandType(), "запуск бота"),
            new BotCommand(KEY.getCommandType(), "выбрать игру"),
            new BotCommand(RESULT.getCommandType(), "моя статистика"),
            new BotCommand(CLEAN.getCommandType(), "удалить статистику"),
            new BotCommand(RULES.getCommandType(), "правила игры"),
            new BotCommand(INFO.getCommandType(), "наши проекты"),
            new BotCommand(DONATE.getCommandType(), "благотворительность")
    );
}
