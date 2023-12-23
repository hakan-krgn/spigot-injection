package com.hakan.spinjection.command.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

/**
 * Default Tab Completer for commands, injected through {@link com.hakan.spinjection.command.annotations.Command} annotation
 */
public class NoTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
