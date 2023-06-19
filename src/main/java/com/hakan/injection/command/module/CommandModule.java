package com.hakan.injection.command.module;

import com.google.inject.Injector;
import com.hakan.injection.command.annotations.Command;
import com.hakan.injection.command.executor.CommandExecutor;
import com.hakan.injection.module.impl.MethodModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * CommandModule registers command executors.
 */
public class CommandModule extends MethodModule<Command> {

    /**
     * Constructor of CommandModule.
     *
     * @param plugin      plugin
     * @param injector    injector
     * @param reflections reflections
     */
    public CommandModule(@Nonnull Plugin plugin,
                         @Nonnull Injector injector,
                         @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Command.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRegister(@Nonnull Method method,
                           @Nonnull Object instance,
                           @Nonnull Command command) {
        if (method.getReturnType() != void.class)
            throw new RuntimeException("event listener method must have void return type!");

        new CommandExecutor(command, instance, method).register();
    }
}
