package com.hakan.spinjection.utils;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.BiFunction;

/**
 * ProxyUtils is the utility class
 * for creating interfaces.
 */
public class ProxyUtils {

	/**
	 * Creates a proxy instance from
	 * the given interface.
	 *
	 * @param clazz    interface
	 * @param function function
	 * @return proxy instance
	 */
	public static @Nonnull Object create(@Nonnull Class<?> clazz,
										 @Nonnull ProxyFunction function) {
		return Proxy.newProxyInstance(
			clazz.getClassLoader(),
			new Class[]{clazz},
			(proxy, method, args) -> function.apply(method, (args == null) ? new Object[0] : args)
		);
	}



	/**
	 * ProxyFunction is the callback function
	 * for {@link Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)}.
	 */
	public interface ProxyFunction extends BiFunction<Method, Object[], Object> {

	}
}
