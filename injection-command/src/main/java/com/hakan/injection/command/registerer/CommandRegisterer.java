package com.hakan.injection.command.registerer;

import com.google.inject.Injector;
import com.hakan.injection.registerer.SpigotRegisterer;
import com.hakan.injection.command.annotations.Command;
import com.hakan.injection.command.executor.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * CommandModule registers command executors.
 */
public class CommandRegisterer extends SpigotRegisterer<Method, Command> {

    /**
     * Constructor of CommandModule.
     *
     * @param plugin      plugin
     * @param injector    injector
     * @param reflections reflections
     */
    public CommandRegisterer(@Nonnull Plugin plugin,
                             @Nonnull Injector injector,
                             @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Method.class, Command.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRegister(@Nullable Object instance,
                           @Nonnull Method method,
                           @Nonnull Command command) {
        if (instance == null)
            throw new RuntimeException("event listener instance cannot be null!");
        if (method.getReturnType() != void.class)
            throw new RuntimeException("event listener method must have void return type!");

        new CommandExecutor(instance, method, command).execute();
    }
}
