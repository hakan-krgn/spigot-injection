package com.hakan.spinjection.filter;

import com.hakan.basicdi.Injector;
import com.hakan.basicdi.utils.AnnotationUtils;
import com.hakan.spinjection.annotations.Filter;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FilterEngine is a class that
 * runs filter methods which are
 * annotated with {@link Filter}.
 * <p>
 * If the filter method returns true,
 * the method will be executed.
 */
public class FilterEngine {

    private static final Pattern PARAM_REGEX = Pattern.compile("(@[a-zA-Z0-9]+)");
    private static final Pattern SERVICE_REGEX = Pattern.compile("(#[a-zA-Z0-9]+)");



    private final Injector injector;
    private final ScriptEngine engine;

    /**
     * Constructor of {@link FilterEngine}.
     *
     * @param injector injector
     */
    public FilterEngine(@Nonnull Injector injector) {
        this.injector = injector;
        this.engine = new ScriptEngineManager(null)
                .getEngineByName("nashorn");
    }

    /**
     * Runs the filter method.
     *
     * @param method method
     * @param args   arguments
     * @return result
     */
    @SneakyThrows
    public boolean run(@Nonnull Method method,
                       @Nonnull Object[] args) {
        if (!AnnotationUtils.isPresent(method, Filter.class))
            return true;
        if (method.getParameterCount() != args.length)
            throw new Exception("parameter count is not equal!");

        return this.run(method.getAnnotation(Filter.class).value(), args);
    }

    /**
     * Runs the filter method.
     *
     * @param filter filter
     * @param args   arguments
     * @return result
     */
    @SneakyThrows
    public boolean run(@Nonnull String filter,
                       @Nonnull Object[] args) {
        Matcher paramMatcher = PARAM_REGEX.matcher(filter);
        Matcher serviceMatcher = SERVICE_REGEX.matcher(filter);


        Bindings bindings = this.engine.createBindings();

        while (paramMatcher.find()) {
            String param = paramMatcher.group();
            int paramIndex = Integer.parseInt(param.substring(4));

            bindings.put(param, args[paramIndex]);
        }

        while (serviceMatcher.find()) {
            String service = serviceMatcher.group();
            String serviceName = service.substring(1);
            String wanted = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1);

            Object instance = this.injector.getModule().getEntities().stream()
                    .filter(entity -> entity.getType().getSimpleName().equals(wanted))
                    .findFirst()
                    .orElseThrow(() -> new Exception("entity not found!"))
                    .getInstance();

            bindings.put(service, instance);
        }

        return (boolean) this.engine.eval(filter, bindings);
    }
}
