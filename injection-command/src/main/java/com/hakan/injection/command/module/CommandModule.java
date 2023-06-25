package com.hakan.injection.command.module;

import com.google.inject.Injector;
import com.hakan.injection.command.annotations.Command;
import com.hakan.injection.command.executor.CommandExecutor;
import com.hakan.injection.executor.SpigotExecutor;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * CommandModule registers command executors.
 */
@SuppressWarnings({"rawtypes"})
public class CommandModule extends SpigotModule<Class, Command> {

    /**
     * Constructor of CommandModule.
     *
     * @param plugin      plugin
     * @param reflections reflections
     */
    public CommandModule(@Nonnull Plugin plugin,
                         @Nonnull Reflections reflections) {
        super(plugin, reflections, Class.class, Command.class);
    }

    /**
     * Loads classes which are annotated with {@link Command}.
     * And creates {@link CommandExecutor} for each class to
     * handle command processes.
     *
     * @param classes classes that are annotated with {@link Command}.
     */
    @Override
    public void load(@Nonnull Set<Class> classes) {
        for (Class clazz : classes) {
            super.executors.add(new CommandExecutor(clazz));
        }
    }

    /**
     * Executes all command executors which are
     * saved in {@link #executors}.
     * <p>
     * Then it runs execute method of each executor.
     *
     * @param injector injector
     */
    @Override
    public void execute(@Nonnull Injector injector) {
        for (SpigotExecutor executor : super.executors) {
            executor.execute(injector.getInstance(executor.getDeclaringClass()), injector);
        }
    }
}
