package com.hakan.injection.module;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.hakan.injection.executor.SpigotExecutor;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * SpigotModule registers fields, methods, constructors and classes
 * that are annotated with the given annotation and are assignable
 * from the given target.
 * <p>
 * Annotation must be assignable from the class types.
 *
 * @param <A> Annotation class type
 */
@SuppressWarnings({"unchecked"})
public abstract class SpigotModule<T, A extends Annotation> extends AbstractModule {

    protected final Plugin plugin;
    protected final Reflections reflections;
    protected final Class<T> target;
    protected final Class<A> annotation;
    protected final List<SpigotExecutor> executors;

    /**
     * Constructor of SpigotModule.
     *
     * @param plugin      plugin instance
     * @param reflections reflections
     * @param target      target type
     * @param annotation  annotation
     */
    public SpigotModule(@Nonnull Plugin plugin,
                        @Nonnull Reflections reflections,

                        @Nonnull Class<T> target,
                        @Nonnull Class<A> annotation) {
        this.plugin = plugin;
        this.reflections = reflections;

        this.target = target;
        this.annotation = annotation;
        this.executors = new ArrayList<>();
    }

    /**
     * Gets the plugin instance.
     *
     * @return plugin instance
     */
    public @Nonnull Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * Gets the reflections instance.
     *
     * @return reflections instance
     */
    public @Nonnull Reflections getReflections() {
        return this.reflections;
    }

    /**
     * Gets the target type.
     *
     * @return target type
     */
    public @Nonnull Class<T> getTarget() {
        return this.target;
    }

    /**
     * Gets the annotation type.
     *
     * @return annotation type
     */
    public @Nonnull Class<A> getAnnotation() {
        return this.annotation;
    }

    /**
     * Gets the executor list.
     *
     * @return executor list
     */
    public @Nonnull List<SpigotExecutor> getExecutors() {
        return this.executors;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        if (this.target.equals(Class.class)) {
            this.load((Set<T>) this.reflections.getTypesAnnotatedWith(this.annotation));
        } else if (this.target.equals(Constructor.class)) {
            this.load((Set<T>) this.reflections.getConstructorsAnnotatedWith(this.annotation));
        } else if (this.target.equals(Method.class)) {
            this.load((Set<T>) this.reflections.getMethodsAnnotatedWith(this.annotation));
        } else if (this.target.equals(Field.class)) {
            this.load((Set<T>) this.reflections.getFieldsAnnotatedWith(this.annotation));
        }
    }


    /**
     * Abstract load method.
     * This method is called when the
     * module installed.
     */
    public abstract void load(@Nonnull Set<T> list);

    /**
     * Abstract execute method.
     * This method is called when the
     * module started.
     */
    public abstract void execute(@Nonnull Injector injector);
}
