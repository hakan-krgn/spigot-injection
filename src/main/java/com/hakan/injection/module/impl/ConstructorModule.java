package com.hakan.injection.module.impl;

import com.google.inject.Injector;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * ConstructorModule registers constructors
 * that are annotated with the annotation.
 *
 * @param <A> annotation type
 */
public abstract class ConstructorModule<A extends Annotation> extends SpigotModule {

    protected final Injector injector;
    protected final Reflections reflections;
    protected final Class<A> annotationClass;

    /**
     * Constructor of ConstructorModule.
     *
     * @param plugin          plugin
     * @param injector        injector
     * @param reflections     reflections
     * @param annotationClass annotation
     */
    public ConstructorModule(@Nonnull Plugin plugin,
                             @Nonnull Injector injector,
                             @Nonnull Reflections reflections,
                             @Nonnull Class<A> annotationClass) {
        super(plugin);
        this.injector = injector;
        this.reflections = reflections;
        this.annotationClass = annotationClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        for (Constructor<?> constructor : this.reflections.getConstructorsAnnotatedWith(this.annotationClass)) {
            constructor.setAccessible(true);

            A annotation = constructor.getAnnotation(this.annotationClass);
            Object instance = this.injector.getInstance(constructor.getDeclaringClass());

            this.onRegister(constructor, instance, annotation);
        }
    }


    /**
     * Returns all constructor that are
     * annotated with the annotation
     * step by step.
     *
     * @param constructor constructor
     * @param instance    class of constructor
     * @param annotation  annotation of constructor
     */
    public abstract void onRegister(@Nonnull Constructor<?> constructor,
                                    @Nonnull Object instance,
                                    @Nonnull A annotation);
}
