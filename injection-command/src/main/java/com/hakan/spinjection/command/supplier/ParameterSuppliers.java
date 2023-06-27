package com.hakan.spinjection.command.supplier;

import com.hakan.spinjection.command.exceptions.InvalidParameterTypeException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * ParameterSuppliers class to
 * get, register and convert
 * parameter to the desired type.
 */
@SuppressWarnings({"unchecked"})
public class ParameterSuppliers {

    private static final Map<Class<?>, ParameterSupplier<?>> suppliers = new HashMap<>();

    /**
     * Registers a parameter supplier.
     *
     * @param clazz    The class of the parameter.
     * @param supplier The supplier.
     * @param <T>      The type of the parameter.
     */
    public static <T> void register(@Nonnull Class<T> clazz,
                                    @Nonnull ParameterSupplier<T> supplier) {
        suppliers.put(clazz, supplier);
    }

    /**
     * Applies a parameter supplier.
     *
     * @param clazz     The class of the parameter.
     * @param parameter The parameter.
     * @param <T>       The type of the parameter.
     * @return The parameter.
     */
    public static @Nonnull <T> T apply(@Nonnull Class<T> clazz,
                                       @Nonnull String parameter) {
        try {
            return (T) suppliers.get(clazz).get(parameter);
        } catch (Exception e) {
            throw new InvalidParameterTypeException("could not apply parameter for " + clazz.getName());
        }
    }



    static {
        register(String.class, parameter -> parameter);

        register(boolean.class, Boolean::parseBoolean);
        register(Boolean.class, Boolean::parseBoolean);

        register(byte.class, Byte::parseByte);
        register(Byte.class, Byte::parseByte);

        register(short.class, Short::parseShort);
        register(Short.class, Short::parseShort);

        register(int.class, Integer::parseInt);
        register(Integer.class, Integer::parseInt);

        register(long.class, Long::parseLong);
        register(Long.class, Long::parseLong);

        register(float.class, Float::parseFloat);
        register(Float.class, Float::parseFloat);

        register(double.class, Double::parseDouble);
        register(Double.class, Double::parseDouble);

        register(World.class, Bukkit::getWorld);
        register(Player.class, Bukkit::getPlayer);
    }
}
