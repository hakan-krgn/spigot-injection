package com.hakan.spinjection.config.executor;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.annotations.ConfigValue;
import com.hakan.spinjection.config.container.Container;
import com.hakan.spinjection.config.container.ContainerFactory;
import com.hakan.spinjection.config.schedulers.ConfigReloadScheduler;
import com.hakan.spinjection.config.schedulers.ConfigSaveScheduler;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.utils.ProxyUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * ConfigExecutor is an executor class
 * that is used to execute ConfigValue methods.
 */
public class ConfigExecutor implements SpigotExecutor {

    private Container container;
    private final Object instance;
    private final Class<?> clazz;
    private final ConfigFile annotation;

    /**
     * Constructor of {@link ConfigExecutor}.
     *
     * @param clazz class
     */
    public ConfigExecutor(@Nonnull Class<?> clazz) {
        this.clazz = clazz;
        this.annotation = clazz.getAnnotation(ConfigFile.class);
        this.instance = ProxyUtils.create(this.clazz, this::preCall);
    }

    /**
     * Gets the instance of the class
     * that is annotated with {@link ConfigFile}.
     *
     * @return instance
     */
    @Override
    public @Nonnull Object getInstance() {
        return this.instance;
    }

    /**
     * Gets the class of the instance
     * that is annotated with {@link ConfigFile}.
     *
     * @return class
     */
    @Override
    public @Nonnull Class<?> getDeclaringClass() {
        return this.clazz;
    }



    /**
     * Creates config file if not exists
     * and creates container from the config file.
     * <p>
     * Then it starts the config reload and save scheduler.
     *
     * @param bootstrap injector
     * @param instance  instance
     */
    @Override
    public void execute(@Nonnull SpigotBootstrap bootstrap,
                        @Nonnull Object instance) {
        this.container = ContainerFactory.of(this.annotation);

        new ConfigReloadScheduler(bootstrap.getPlugin(), this.container, this.annotation).start();
        new ConfigSaveScheduler(bootstrap.getPlugin(), this.container, this.annotation).start();
    }

    /**
     * Runs when the method from interface
     * of config method is called.
     *
     * @param method method
     * @param args   arguments
     * @return method result
     */
    public @Nullable Object preCall(@Nonnull Method method,
                                    @Nonnull Object[] args) {
        if (method.getName().equals("toString"))
            return this.clazz.getName() + "@" + Integer.toHexString(this.hashCode());
        if (method.getName().equals("hashCode"))
            return this.hashCode();

        if (method.getName().equals("get") && args.length == 1)
            return this.container.get(args[0].toString());
        if (method.getName().equals("get") && args.length == 2)
            return this.container.get(args[0].toString(), args[1]);
        if (method.getName().equals("set") && args.length == 2)
            return this.container.set(args[0].toString(), args[1]);
        if (method.getName().equals("set") && args.length == 3)
            return this.container.set(args[0].toString(), args[1], (boolean) args[2]);
        if (method.getName().equals("save") && args.length == 0)
            return this.container.save();
        if (method.getName().equals("reload") && args.length == 0)
            return this.container.reload();

        return this.postCall(method, args);
    }

    /**
     * Executes the methods which are
     * annotated with {@link ConfigValue} annotation.
     *
     * @param method method
     * @return method result
     */
    public @Nullable Object postCall(@Nonnull Method method,
                                     @Nonnull Object[] args) {
        if (!method.isAnnotationPresent(ConfigValue.class))
            throw new RuntimeException("method is not registered!");
        if (method.getParameterCount() > 1)
            throw new RuntimeException("parameter count must be 0 or 1!");


        return method.getParameterCount() == 0 ?
                this.container.get(method.getAnnotation(ConfigValue.class)) :
                this.container.get(method.getAnnotation(ConfigValue.class), args[0]);
    }
}
