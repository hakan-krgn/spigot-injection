package com.hakan.injection.scanner;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.hakan.injection.module.impl.ClassModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;

public class ScannerModule extends ClassModule<Singleton> {

    public ScannerModule(@Nonnull Plugin plugin,
                         @Nonnull Injector injector,
                         @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Singleton.class);
    }

    @Override
    public void onRegister(@Nonnull Class<?> clazz,
                           @Nonnull Object instance,
                           @Nonnull Singleton annotation) {
        this.bind(clazz);
    }
}
