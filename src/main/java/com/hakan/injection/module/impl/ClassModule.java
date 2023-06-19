package com.hakan.injection.module.impl;

import com.google.inject.Injector;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

public abstract class ClassModule<A extends Annotation> extends SpigotModule {

    protected final Injector injector;
    protected final Reflections reflections;
    protected final Class<A> annotationClass;

    public ClassModule(@Nonnull Plugin plugin,
                       @Nonnull Injector injector,
                       @Nonnull Reflections reflections,
                       @Nonnull Class<A> annotationClass) {
        super(plugin);
        this.injector = injector;
        this.reflections = reflections;
        this.annotationClass = annotationClass;
    }

    @Override
    protected void configure() {
        for (Class<?> clazz : this.reflections.getTypesAnnotatedWith(this.annotationClass)) {
            A annotation = clazz.getAnnotation(this.annotationClass);
            Object instance = this.injector.getInstance(clazz);

            this.onRegister(clazz, instance, annotation);
        }
    }


    public abstract void onRegister(@Nonnull Class<?> clazz,
                                    @Nonnull Object instance,
                                    @Nonnull A annotation);
}
