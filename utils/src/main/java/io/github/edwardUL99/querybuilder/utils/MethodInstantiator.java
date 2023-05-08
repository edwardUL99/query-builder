package io.github.edwardUL99.querybuilder.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Allows for instantiation through a static creator method
 */
public class MethodInstantiator implements Instantiator {
    /**
     * The name of the method
     */
    private final String methodName;
    /**
     * The expected method return type. The type of the method found must be assignable to this
     */
    private final Class<?> returnType;

    public MethodInstantiator(String methodName, Class<?> returnType) {
        this.methodName = methodName;
        this.returnType = returnType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T instantiate(Class<T> type, Class<?>[] argumentTypes, Object... args) {
        try {
            Method method = type.getDeclaredMethod(methodName, argumentTypes);

            if (!Modifier.isStatic(method.getModifiers())) {
                throw new IllegalStateException("The method must be static");
            } else if (!returnType.isAssignableFrom(method.getReturnType())) {
                throw new IllegalStateException("The method's return type must be assignable to: " + returnType.getName());
            } else {
                return (T) method.invoke(null, args);
            }
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Failed to instantiate", ex);
        }
    }
}
