package com.hakan.injection.module.impl;

import com.google.inject.Injector;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

/**
 * ClassModule registers classes
 * that are annotated with the annotation.
 *
 * @param <A> annotation type
 */
public abstract class ClassModule<A extends Annotation> extends SpigotModule {

    protected final Injector injector;
    protected final Reflections reflections;
    protected final Class<A> annotationClass;

    /**
     * Constructor of ClassModule.
     *
     * @param plugin          plugin
     * @param injector        injector
     * @param reflections     reflections
     * @param annotationClass annotation
     */
    public ClassModule(@Nonnull Plugin plugin,
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
        for (Class<?> clazz : this.reflections.getTypesAnnotatedWith(this.annotationClass)) {
            A annotation = clazz.getAnnotation(this.annotationClass);
            Object instance = this.injector.getInstance(clazz);

            this.onRegister(clazz, instance, annotation);
        }
    }


    /**
     * Returns all class that are
     * annotated with the annotation
     * step by step.
     *
     * @param clazz      class
     * @param instance   class of class
     * @param annotation annotation of class
     */
    public abstract void onRegister(
            @Nonnull Class<?> clazz,
            @Nonnull Object instance,
            @Nonnull A annotation
    );
}
