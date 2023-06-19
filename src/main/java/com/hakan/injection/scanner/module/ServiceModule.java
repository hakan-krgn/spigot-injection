package com.hakan.injection.scanner.module;

import com.google.inject.Injector;
import com.hakan.injection.module.impl.ClassModule;
import com.hakan.injection.scanner.annotations.Service;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;

/**
 * ScannerModule registers classes
 * that are annotated with Singleton.
 */
public class ServiceModule extends ClassModule<Service> {

    /**
     * Constructor of ScannerModule.
     *
     * @param plugin      plugin
     * @param injector    injector
     * @param reflections reflections
     */
    public ServiceModule(@Nonnull Plugin plugin,
                         @Nonnull Injector injector,
                         @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Service.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRegister(@Nonnull Class<?> clazz,
                           @Nonnull Object instance,
                           @Nonnull Service annotation) {
        this.bind(clazz);
    }
}
