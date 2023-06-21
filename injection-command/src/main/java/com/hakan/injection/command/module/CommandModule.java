package com.hakan.injection.command.module;

import com.google.inject.Injector;
import com.hakan.injection.command.annotations.Command;
import com.hakan.injection.command.executor.CommandExecutor;
import com.hakan.injection.executor.SpigotExecutor;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * CommandModule registers command executors.
 */
public class CommandModule extends SpigotModule<Method, Command> {

    /**
     * Constructor of CommandModule.
     *
     * @param plugin      plugin
     * @param reflections reflections
     */
    public CommandModule(@Nonnull Plugin plugin,
                         @Nonnull Reflections reflections) {
        super(plugin, reflections, Method.class, Command.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(@Nonnull Set<Method> methods) {
        for (Method method : methods) {
            if (method.getReturnType() != void.class)
                throw new RuntimeException("event listener method must have void return type!");

            super.executors.add(new CommandExecutor(method));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@Nonnull Injector injector) {
        for (SpigotExecutor executor : super.executors) {
            executor.execute(injector.getInstance(executor.getDeclaringClass()));
        }
    }
}
