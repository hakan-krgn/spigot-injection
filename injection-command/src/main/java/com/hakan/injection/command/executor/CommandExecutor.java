package com.hakan.injection.command.executor;

import com.google.inject.Injector;
import com.hakan.injection.command.annotations.Command;
import com.hakan.injection.command.annotations.CommandParam;
import com.hakan.injection.command.supplier.ParameterSuppliers;
import com.hakan.injection.command.utils.CommandUtils;
import com.hakan.injection.executor.SpigotExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.reflections.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * Command executor to
 * listen bukkit commands and
 * invoke related method.
 */
public class CommandExecutor extends BukkitCommand implements SpigotExecutor {

    private Object instance;
    private final Method method;

    /**
     * Constructor of {@link CommandExecutor}.
     *
     * @param method method
     */
    public CommandExecutor(@Nonnull Method method) {
        this(method, method.getAnnotation(Command.class));
    }

    /**
     * Constructor of {@link CommandExecutor}.
     *
     * @param method  method
     * @param command annotation
     */
    public CommandExecutor(@Nonnull Method method,
                           @Nonnull Command command) {
        super(command.name(), command.description(), command.usage(), Arrays.asList(command.aliases()));
        this.method = method;
    }

    /**
     * Gets the method of the
     * command executor.
     *
     * @return method
     */
    public @Nonnull Method getMethod() {
        return this.method;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Object getInstance() {
        return this.instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Class<?> getDeclaringClass() {
        return this.method.getDeclaringClass();
    }

    /**
     * Registers the command to
     * bukkit command map and routes
     * the command to this executor.
     */
    @Override
    public void execute(@Nonnull Object instance,
                        @Nonnull Injector injector) {
        this.instance = instance;
        CommandUtils.register(this);
    }


    /**
     * Executes the command, returning its success.
     *
     * @param sender the sender of the command
     * @param label  the command label
     * @param args   the command arguments
     * @return true if the command was successful
     */
    @Override
    public boolean execute(@Nonnull CommandSender sender,
                           @Nonnull String label,
                           @Nonnull String[] args) {
        Parameter[] parameters = this.method.getParameters();
        Object[] objects = new Object[parameters.length];

        objects[0] = sender;

        for (int i = 1; i < parameters.length; i++) {
            if (!parameters[i].isAnnotationPresent(CommandParam.class))
                throw new RuntimeException("parameter must be annotated with @CommandParam!");


            String parameter = args[i - 1];
            Class<?> parameterType = parameters[i].getType();

            objects[i] = ParameterSuppliers.apply(parameterType, parameter);
        }

        ReflectionUtils.invoke(this.method, this.instance, objects);
        return false;
    }
}
