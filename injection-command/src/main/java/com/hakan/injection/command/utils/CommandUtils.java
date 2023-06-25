package com.hakan.injection.command.utils;

import com.hakan.injection.command.executor.CommandExecutor;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import javax.annotation.Nonnull;
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
    @SneakyThrows
    public static void register(@Nonnull CommandExecutor executor) {
        Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);

        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        Command command = commandMap.getCommand(executor.getName());

        if (command != null && command.isRegistered())
            return;

        commandMap.register(executor.getName(), executor);
    }

    /**
     * Checks if the sender has the permission.
     *
     * @param sender     sender
     * @param permission permission
     * @return true if the sender has permission
     */
    public static boolean hasPermission(@Nonnull CommandSender sender,
                                        @Nonnull String permission) {
        return sender.isOp() ||
               sender.hasPermission("*") ||
               sender.hasPermission(permission) ||
               sender instanceof ConsoleCommandSender;
    }
}
