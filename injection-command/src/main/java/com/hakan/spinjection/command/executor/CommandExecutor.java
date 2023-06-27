package com.hakan.spinjection.command.executor;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.command.annotations.Command;
import com.hakan.spinjection.command.annotations.CommandParam;
import com.hakan.spinjection.command.annotations.Executor;
import com.hakan.spinjection.command.annotations.Subcommand;
import com.hakan.spinjection.command.exceptions.InsufficientPermissionException;
import com.hakan.spinjection.command.exceptions.InvalidArgsLengthException;
import com.hakan.spinjection.command.exceptions.InvalidParameterTypeException;
import com.hakan.spinjection.command.exceptions.MissingAnnotationException;
import com.hakan.spinjection.command.supplier.ParameterSuppliers;
import com.hakan.spinjection.command.utils.CommandUtils;
import com.hakan.spinjection.executor.SpigotExecutor;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

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
@SuppressWarnings({"rawtypes"})
public class CommandExecutor extends BukkitCommand implements SpigotExecutor {

    private Object instance;
    private final Class clazz;
    private final Method[] methods;

    /**
     * Constructor of {@link CommandExecutor}.
     *
     * @param clazz clazz
     */
    public CommandExecutor(@Nonnull Class<?> clazz) {
        this(clazz, clazz.getAnnotation(Command.class));
    }

    /**
     * Constructor of {@link CommandExecutor}.
     *
     * @param clazz   clazz
     * @param command annotation
     */
    public CommandExecutor(@Nonnull Class clazz,
                           @Nonnull Command command) {
        super(
                command.name(),
                command.description(),
                command.usage(),
                Arrays.asList(command.aliases())
        );

        this.clazz = clazz;
        this.methods = Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(Subcommand.class))
                .toArray(Method[]::new);
    }

    /**
     * Gets the instance of the class
     * that is annotated with {@link Command}.
     *
     * @return the instance of the executor
     */
    @Override
    public @Nullable Object getInstance() {
        return this.instance;
    }

    /**
     * Gets the class of the instance
     * that is annotated with {@link Command}.
     *
     * @return the class of the instance
     */
    @Override
    public @Nonnull Class<?> getDeclaringClass() {
        return this.clazz;
    }



    /**
     * Registers the command to
     * bukkit command map and routes
     * the command to this executor.
     */
    @Override
    public void execute(@Nonnull SpigotBootstrap bootstrap,
                        @Nonnull Object instance) {
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
        for (Method method : this.methods) {
            try {
                this.invoke(method, sender, args);
            } catch (InsufficientPermissionException e) {
                sender.sendMessage(e.getMessage());
            } catch (MissingAnnotationException e) {
                e.printStackTrace();
            } catch (InvalidArgsLengthException | InvalidParameterTypeException ignored) {

            }
        }

        return true;
    }

    /**
     * Invokes the method with the
     * given parameters, and if the
     * exception is thrown, it is
     * caught.
     *
     * @param method method
     * @param sender sender
     * @param args   args
     */
    @SneakyThrows
    public void invoke(@Nonnull Method method,
                       @Nonnull CommandSender sender,
                       @Nonnull String[] args) {
        Parameter[] parameters = method.getParameters();
        Object[] objects = new Object[parameters.length];
        Subcommand subcommand = method.getAnnotation(Subcommand.class);

        String permission = subcommand.permission();
        String permissionMessage = subcommand.permissionMessage();

        if (args.length != parameters.length - 1) {
            throw new InvalidArgsLengthException("args length must be " + (parameters.length - 1));
        } else if (!permission.isEmpty() && !CommandUtils.hasPermission(sender, permission)) {
            throw new InsufficientPermissionException(permissionMessage);
        }

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(Executor.class)) {
                objects[i] = sender;
            } else if (parameters[i].isAnnotationPresent(CommandParam.class)) {
                objects[i] = ParameterSuppliers.apply(parameters[i].getType(), args[i - 1]);
            } else {
                throw new MissingAnnotationException("parameter must be annotated with @CommandParam or @Executor");
            }
        }

        method.invoke(this.instance, objects);
    }
}
