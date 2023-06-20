package com.hakan.injection.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.hakan.injection.SpigotRegisterer;
import com.hakan.injection.annotations.Component;
import com.hakan.injection.annotations.Service;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;

/**
 * ScannerModule registers classes
 * that are annotated with the type annotation.
 */
public class ScannerModule extends AbstractModule {

    private final Injector injector;
    private final Reflections reflections;

    /**
     * Constructor of ScannerModule.
     *
     * @param injector    injector
     * @param reflections reflections
     */
    public ScannerModule(@Nonnull Injector injector,
                         @Nonnull Reflections reflections) {
        this.injector = injector;
        this.reflections = reflections;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        this.bind(Plugin.class).toInstance(this.injector.getInstance(Plugin.class));
        this.bind(Injector.class).toInstance(this.injector);
        this.bind(Reflections.class).toInstance(this.reflections);

        this.reflections.getSubTypesOf(AbstractModule.class).forEach(this::bind);
        this.reflections.getSubTypesOf(AbstractModule.class).forEach(this::install);

        this.reflections.getTypesAnnotatedWith(Service.class).forEach(this::bind);
        this.reflections.getTypesAnnotatedWith(Component.class).forEach(this::bind);

        this.reflections.getSubTypesOf(SpigotRegisterer.class)
                .forEach((c) -> this.injector.getInstance(c).register());
    }



    /**
     * Binds the class to
     * instance of itself.
     *
     * @param clazz class
     */
    private void install(@Nonnull Class<?> clazz) {
        try {
            this.install((AbstractModule) this.injector.getInstance(clazz));
        } catch (Exception e) {
            throw new RuntimeException("cannot create instance of " + clazz.getName(), e);
        }
    }
}
