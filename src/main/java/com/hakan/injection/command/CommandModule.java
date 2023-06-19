package com.hakan.injection.command;

import com.google.inject.Injector;
import com.hakan.injection.command.annotations.Command;
import com.hakan.injection.module.impl.MethodModule;
import com.hakan.injection.utils.CommandUtils;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public class CommandModule extends MethodModule<Command> {

    public CommandModule(@Nonnull Plugin plugin,
                         @Nonnull Injector injector,
                         @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Command.class);
    }

    @Override
    public void onRegister(@Nonnull Method method,
                           @Nonnull Object instance,
                           @Nonnull Command command) {
        if (method.getReturnType() != void.class)
            throw new RuntimeException("event listener method must have void return type!");

        CommandUtils.register(new CommandExecutor(command, instance, method));
    }
}
