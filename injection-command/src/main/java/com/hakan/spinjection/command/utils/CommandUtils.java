package com.hakan.spinjection.command.utils;

import com.hakan.spinjection.command.exceptions.TabCompleterException;
import com.hakan.spinjection.command.executor.CommandExecutor;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
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

        // Creation of PluginCommand (Command doesn't have #setTabCompleter)
        Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
        constructor.setAccessible(true);
        PluginCommand pluginCommand = constructor.newInstance(executor.getName(), executor.getPlugin());

        pluginCommand.setExecutor(null);

        // Registration of tab completer
        com.hakan.spinjection.command.annotations.Command annotation = executor.getDeclaringClass().getAnnotation(com.hakan.spinjection.command.annotations.Command.class);
        Class<?> tabCompleterClazz = annotation.tabCompleter();

        if (tabCompleterClazz != null) {
            Object tabCompleter = tabCompleterClazz.getConstructor().newInstance();
            if (tabCompleter instanceof TabCompleter) {
                pluginCommand.setTabCompleter((TabCompleter) tabCompleter);
            } else {
                throw new TabCompleterException(executor.getDeclaringClass() + "'s tabcompleter must implement TabCompleter.class");
            }
        }

        commandMap.register(executor.getPlugin().getName(), pluginCommand);
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
