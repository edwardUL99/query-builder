package io.github.edwardUL99.querybuilder.utils;

/**
 * Instantiates using a constructor
 */
public class ConstructorInstantiator implements Instantiator {
    @Override
    public <T> T instantiate(Class<T> type, Class<?>[] argumentTypes, Object...args) {
        try {
            return type.getDeclaredConstructor(argumentTypes).newInstance(args);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Failed to instantiate", ex);
        }
    }
}
