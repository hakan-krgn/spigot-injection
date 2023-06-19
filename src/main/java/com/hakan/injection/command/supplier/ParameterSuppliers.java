package com.hakan.injection.command.supplier;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public class ParameterSuppliers {

    private static final ParameterSupplier<String> STRING_SUPPLIER = parameter -> parameter;
    private static final ParameterSupplier<Boolean> BOOLEAN_SUPPLIER = Boolean::parseBoolean;

    private static final ParameterSupplier<Byte> BYTE_SUPPLIER = Byte::parseByte;
    private static final ParameterSupplier<Short> SHORT_SUPPLIER = Short::parseShort;
    private static final ParameterSupplier<Integer> INTEGER_SUPPLIER = Integer::parseInt;
    private static final ParameterSupplier<Long> LONG_SUPPLIER = Long::parseLong;

    private static final ParameterSupplier<Float> FLOAT_SUPPLIER = Float::parseFloat;
    private static final ParameterSupplier<Double> DOUBLE_SUPPLIER = Double::parseDouble;

    private static final ParameterSupplier<World> WORLD_SUPPLIER = Bukkit::getWorld;
    private static final ParameterSupplier<Player> PLAYER_SUPPLIER = Bukkit::getPlayer;



    private static final Map<Class<?>, ParameterSupplier<?>> suppliers = new HashMap<>();

    public static @Nonnull <T> T apply(@Nonnull Class<T> clazz,
                                       @Nonnull String parameter) {
        return (T) suppliers.get(clazz).get(parameter);
    }

    public static <T> void register(@Nonnull Class<T> clazz,
                                    @Nonnull ParameterSupplier<T> supplier) {
        suppliers.put(clazz, supplier);
    }



    static {
        register(String.class, STRING_SUPPLIER);

        register(boolean.class, BOOLEAN_SUPPLIER);
        register(Boolean.class, BOOLEAN_SUPPLIER);

        register(byte.class, BYTE_SUPPLIER);
        register(Byte.class, BYTE_SUPPLIER);

        register(short.class, SHORT_SUPPLIER);
        register(Short.class, SHORT_SUPPLIER);

        register(int.class, INTEGER_SUPPLIER);
        register(Integer.class, INTEGER_SUPPLIER);

        register(long.class, LONG_SUPPLIER);
        register(Long.class, LONG_SUPPLIER);

        register(float.class, FLOAT_SUPPLIER);
        register(Float.class, FLOAT_SUPPLIER);

        register(double.class, DOUBLE_SUPPLIER);
        register(Double.class, DOUBLE_SUPPLIER);

        register(World.class, WORLD_SUPPLIER);
        register(Player.class, PLAYER_SUPPLIER);
    }
}
