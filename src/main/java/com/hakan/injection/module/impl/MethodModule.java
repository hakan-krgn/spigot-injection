package com.hakan.injection.module.impl;

import com.google.inject.Injector;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class MethodModule<A extends Annotation> extends SpigotModule {

    protected final Injector injector;
    protected final Reflections reflections;
    protected final Class<A> annotationClass;

    public MethodModule(@Nonnull Plugin plugin,
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
        for (Method method : this.reflections.getMethodsAnnotatedWith(this.annotationClass)) {
            method.setAccessible(true);

            A annotation = method.getAnnotation(this.annotationClass);
            Object instance = this.injector.getInstance(method.getDeclaringClass());

            this.onRegister(method, instance, annotation);
        }
    }


    public abstract void onRegister(@Nonnull Method method,
                                    @Nonnull Object instance,
                                    @Nonnull A annotation);
}
