package com.hakan.injection.config.executor;

import com.google.inject.Injector;
import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.config.annotations.ConfigValue;
import com.hakan.injection.config.container.Container;
import com.hakan.injection.config.container.ContainerFactory;
import com.hakan.injection.config.utils.ConfigUtils;
import com.hakan.injection.executor.SpigotExecutor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ConfigExecutor implements SpigotExecutor {

    private Container container;
    private final Object instance;
    private final Class<?> clazz;
    private final ConfigFile annotation;

    public ConfigExecutor(@Nonnull Class<?> clazz) {
        this.clazz = clazz;
        this.annotation = clazz.getAnnotation(ConfigFile.class);
        this.instance = ConfigUtils.createProxy(this.clazz, this::preCall);
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
        return this.clazz;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@Nonnull Object instance,
                        @Nonnull Injector injector) {
        ConfigUtils.createFile(
                this.annotation.path(),
                this.annotation.resource(),
                this.clazz
        );

        this.container = ContainerFactory.of(instance, this.annotation);
    }


    /**
     * Runs when an interface
     * method is called.
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

        if (method.getName().equals("save"))
            return this.container.save();
        if (method.getName().equals("reload"))
            return this.container.reload();
        if (method.getName().equals("get") && args.length == 1)
            return this.container.get(args[0].toString());
        if (method.getName().equals("get") && args.length == 2)
            return this.container.get(args[0].toString(), (Class<?>) args[1]);
        if (method.getName().equals("set") && args.length == 2)
            return this.container.set(args[0].toString(), args[1]);
        if (method.getName().equals("set") && args.length == 3)
            return this.container.set(args[0].toString(), args[1], (boolean) args[2]);

        return this.postCall(method, args);
    }

    /**
     * Executes the methods which are
     * annotated with {@link ConfigValue} annotation.
     *
     * @param method method
     * @param args   arguments
     * @return method result
     */
    public @Nullable Object postCall(@Nonnull Method method,
                                     @Nonnull Object[] args) {
        if (!method.isAnnotationPresent(ConfigValue.class))
            throw new RuntimeException("method is not registered!");
        if (method.getParameterCount() != 0)
            throw new RuntimeException("parameter count must be 0!");

        ConfigValue annotation = method.getAnnotation(ConfigValue.class);
        return this.container.get(annotation.value(), method.getReturnType());
    }
}
