package com.hakan.injection;

import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * SpigotRegisterer registers fields, methods, constructors and classes
 * that are annotated with the given annotation and are assignable
 * from the given target.
 * <p>
 * Target and annotation must be assignable from the class types.
 *
 * @param <T> Target class type
 * @param <A> Annotation class type
 */
@SuppressWarnings({"unchecked"})
public abstract class SpigotRegisterer<T, A extends Annotation> {

    protected final Plugin plugin;
    protected final Injector injector;
    protected final Reflections reflections;

    protected final Class<T> target;
    protected final Class<A> annotation;

    /**
     * Constructor of SpigotRegisterer.
     *
     * @param plugin      plugin instance
     * @param injector    injector
     * @param reflections reflections
     * @param target      target
     * @param annotation  annotation
     */
    public SpigotRegisterer(@Nonnull Plugin plugin,
                            @Nonnull Injector injector,
                            @Nonnull Reflections reflections,
                            @Nonnull Class<T> target,
                            @Nonnull Class<A> annotation) {
        this.plugin = plugin;
        this.injector = injector;
        this.reflections = reflections;

        this.target = target;
        this.annotation = annotation;
    }

    /**
     * Registers the target classes
     * that are annotated with the given annotation.
     */
    public void register() {
        if (this.target.equals(Field.class)) {
            this.reflections.getFieldsAnnotatedWith(this.annotation).forEach(this::registerField);
        } else if (this.target.equals(Method.class)) {
            this.reflections.getMethodsAnnotatedWith(this.annotation).forEach(this::registerMethod);
        } else if (this.target.equals(Constructor.class)) {
            this.reflections.getConstructorsWithParameter(this.annotation).forEach(this::registerConstructor);
        } else if (this.target.equals(Class.class)) {
            this.reflections.getTypesAnnotatedWith(this.annotation).forEach(this::registerClass);
        } else {
            throw new RuntimeException("invalid target class!");
        }
    }



    /**
     * Routes the target to the
     * abstract onRegister method.
     *
     * @param field field
     */
    private void registerField(Field field) {
        field.setAccessible(true);

        A annotation = field.getAnnotation(this.annotation);
        Object instance = this.injector.getInstance(field.getDeclaringClass());

        this.onRegister(instance, (T) field, annotation);
    }

    /**
     * Routes the target to the
     * abstract onRegister method.
     *
     * @param method method
     */
    private void registerMethod(Method method) {
        method.setAccessible(true);

        A annotation = method.getAnnotation(this.annotation);
        Object instance = this.injector.getInstance(method.getDeclaringClass());

        this.onRegister(instance, (T) method, annotation);
    }

    /**
     * Routes the target to the
     * abstract onRegister method.
     *
     * @param constructor constructor
     */
    private void registerConstructor(Constructor<?> constructor) {
        constructor.setAccessible(true);

        A annotation = constructor.getAnnotation(this.annotation);
        Object instance = this.injector.getInstance(constructor.getDeclaringClass());

        this.onRegister(instance, (T) constructor, annotation);
    }

    /**
     * Routes the target to the
     * abstract onRegister method.
     *
     * @param clazz class
     */
    private void registerClass(Class<?> clazz) {
        A annotation = clazz.getAnnotation(this.annotation);
        Object instance = this.injector.getInstance(clazz);

        this.onRegister(instance, (T) clazz, annotation);
    }



    /**
     * Abstract onRegister method.
     * This method is called when a target is found.
     *
     * @param instance   instance of target class
     * @param target     target class
     * @param annotation wanted annotation
     */
    public abstract void onRegister(
            @Nonnull Object instance,
            @Nonnull T target,
            @Nonnull A annotation
    );
}
