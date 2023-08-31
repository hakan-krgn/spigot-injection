package com.hakan.spinjection.utils;

import com.hakan.spinjection.annotations.Async;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ReflectionUtils is a utility class
 * for Reflections library.
 */
@SuppressWarnings({"unchecked"})
public class ReflectionUtils {

	/**
	 * Gets field value from the instance.
	 *
	 * @param instance instance
	 * @param field    field
	 * @param <T>      type
	 * @return value
	 */
	@SneakyThrows
	public static @Nonnull <T> T getValue(@Nonnull Object instance,
										  @Nonnull String field) {
		Field declaredField = instance.getClass().getDeclaredField(field);
		declaredField.setAccessible(true);

		return (T) declaredField.get(instance);
	}

	/**
	 * Invokes method from the instance.
	 *
	 * @param instance instance
	 * @param args     arguments
	 */
	@SneakyThrows
	public static void invokeMethod(@Nonnull Object instance,
									@Nonnull Method method,
									@Nonnull Object... args) {
		method.setAccessible(true);
		method.invoke(instance, args);
	}

	/**
	 * Runs method from the instance
	 * asynchronously if the method is
	 * specified with Async annotation.
	 *
	 * @param plugin   plugin
	 * @param instance instance
	 * @param method   method
	 * @param args     arguments
	 */
	@SneakyThrows
	public static void runMethod(@Nonnull Plugin plugin,
								 @Nonnull Object instance,
								 @Nonnull Method method,
								 @Nonnull Object... args) {
		if (method.getParameterCount() != args.length)
			throw new RuntimeException("method parameter count and args length not equal!");

		if (!method.isAnnotationPresent(Async.class))
			invokeMethod(instance, method, args);
		else Bukkit.getScheduler().runTaskAsynchronously(plugin,
			() -> invokeMethod(instance, method, args));
	}
}
