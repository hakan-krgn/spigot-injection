package com.hakan.injection.module.impl;

import com.google.inject.Injector;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public abstract class ConstructorModule<A extends Annotation> extends SpigotModule {

    protected final Injector injector;
    protected final Reflections reflections;
    protected final Class<A> annotationClass;

    public ConstructorModule(@Nonnull Plugin plugin,
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
        for (Constructor<?> constructor : this.reflections.getConstructorsAnnotatedWith(this.annotationClass)) {
            constructor.setAccessible(true);

            A annotation = constructor.getAnnotation(this.annotationClass);
            Object instance = this.injector.getInstance(constructor.getDeclaringClass());

            this.onRegister(constructor, instance, annotation);
        }
    }


    public abstract void onRegister(@Nonnull Constructor<?> constructor,
                                    @Nonnull Object instance,
                                    @Nonnull A annotation);
}
