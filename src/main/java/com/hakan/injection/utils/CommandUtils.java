package com.hakan.injection.utils;

import com.hakan.injection.command.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * This class is used to handle
 * bukkit command processes.
 */
public class CommandUtils {

    /**
     * Registers the command to bukkit command map.
     *
     * @param executor the command executor
     */
    public static void register(@Nonnull CommandExecutor executor) {
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
