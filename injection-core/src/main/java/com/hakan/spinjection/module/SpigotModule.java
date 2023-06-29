package com.hakan.spinjection.module;

import com.hakan.injection.module.Module;
import com.hakan.injection.reflection.Reflection;
import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.annotations.ExecutorOrder;
import com.hakan.spinjection.executor.SpigotExecutor;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
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
public abstract class SpigotModule<T, A extends Annotation> extends Module implements Comparable<SpigotModule<?, ?>> {

    protected final Plugin plugin;
    protected final Reflection reflections;
    protected final SpigotBootstrap bootstrap;
    protected final Class<T> target;
    protected final Class<A> annotation;
    protected final Set<SpigotExecutor> executors;
    protected final int priority;

    /**
     * Constructor of {@link SpigotModule}.
     *
     * @param bootstrap  bootstrap
     * @param target     target type
     * @param annotation annotation
     */
    public SpigotModule(@Nonnull SpigotBootstrap bootstrap,
                        @Nonnull Class<T> target,
                        @Nonnull Class<A> annotation) {
        this.bootstrap = bootstrap;
        this.plugin = bootstrap.getPlugin();
        this.reflections = bootstrap.getReflection();

        this.target = target;
        this.annotation = annotation;
        this.executors = new HashSet<>();
        this.priority = this.getClass().isAnnotationPresent(ExecutorOrder.class) ?
                this.getClass().getAnnotation(ExecutorOrder.class).value() : 100;
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
     * Gets the reflection instance.
     *
     * @return reflections instance
     */
    public @Nonnull Reflection getReflections() {
        return this.reflections;
    }

    /**
     * Gets the bootstrap instance.
     *
     * @return bootstrap instance
     */
    public @Nonnull SpigotBootstrap getBootstrap() {
        return this.bootstrap;
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
    public @Nonnull Set<SpigotExecutor> getExecutors() {
        return this.executors;
    }

    /**
     * Gets the priority.
     *
     * @return priority
     */
    public int getPriority() {
        return this.priority;
    }


    /**
     * Compares this object with the specified object for order.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer
     * as this object is less than, equal to, or greater than
     * the specified object.
     */
    @Override
    public int compareTo(@Nonnull SpigotModule<?, ?> o) {
        return Integer.compare(this.priority, o.priority);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure() {
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
     * module is installed.
     */
    public abstract void load(@Nonnull Set<T> list);

    /**
     * Abstract execute method.
     * This method is called when the
     * module started.
     */
    public abstract void execute();
}
