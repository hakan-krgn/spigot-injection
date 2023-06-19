package com.hakan.injection.module.impl;

import com.google.inject.Injector;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * FieldModule registers fields
 * that are annotated with the annotation.
 *
 * @param <A> annotation type
 */
public abstract class FieldModule<A extends Annotation> extends SpigotModule {

    protected final Injector injector;
    protected final Reflections reflections;
    protected final Class<A> annotationClass;

    /**
     * Constructor of FieldModule.
     *
     * @param plugin          plugin
     * @param injector        injector
     * @param reflections     reflections
     * @param annotationClass annotation
     */
    public FieldModule(@Nonnull Plugin plugin,
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
        for (Field field : this.reflections.getFieldsAnnotatedWith(this.annotationClass)) {
            field.setAccessible(true);

            A annotation = field.getAnnotation(this.annotationClass);
            Object instance = this.injector.getInstance(field.getDeclaringClass());

            this.onRegister(field, instance, annotation);
        }
    }


    /**
     * Returns all field that are
     * annotated with the annotation
     * step by step.
     *
     * @param field      field
     * @param instance   class of field
     * @param annotation annotation of field
     */
    public abstract void onRegister(
            @Nonnull Field field,
            @Nonnull Object instance,
            @Nonnull A annotation
    );
}
