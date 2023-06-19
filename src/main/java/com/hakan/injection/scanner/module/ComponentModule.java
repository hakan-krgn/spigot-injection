package com.hakan.injection.scanner.module;

import com.google.inject.Injector;
import com.hakan.injection.module.impl.ClassModule;
import com.hakan.injection.scanner.annotations.Component;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;

/**
 * ScannerModule registers classes
 * that are annotated with Singleton.
 */
public class ComponentModule extends ClassModule<Component> {

    /**
     * Constructor of ScannerModule.
     *
     * @param plugin      plugin
     * @param injector    injector
     * @param reflections reflections
     */
    public ComponentModule(@Nonnull Plugin plugin,
                           @Nonnull Injector injector,
                           @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Component.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRegister(@Nonnull Class<?> clazz,
                           @Nonnull Object instance,
                           @Nonnull Component annotation) {
        this.bind(clazz);
    }
}
