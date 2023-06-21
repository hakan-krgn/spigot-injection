package com.hakan.injection.command.utils;

import com.hakan.injection.command.executor.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

/**
 * CommandUtils has some utilities
 * for command executors.
 */
public class CommandUtils {

    /**
     * Registers the command to
     * bukkit command map.
     *
     * @param executor command executor
     */
    public static void register(CommandExecutor executor) {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);

            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            org.bukkit.command.Command command = commandMap.getCommand(executor.getName());

            if (command != null && command.isRegistered())
                return;

            commandMap.register(executor.getName(), executor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
