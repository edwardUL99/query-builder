package io.github.edwardUL99.querybuilder.utils;

/**
 * Instantiates objects through necessary means
 */
public interface Instantiator {
    /**
     * Instantiate the object through whatever means, using the provided args
     * @param type the type of the object to instantiate
     * @param argumentTypes the types of arguments required to instantiate the class
     * @param args the arguments
     * @return the instantiated object
     */
    <T> T instantiate(Class<T> type, Class<?>[] argumentTypes, Object...args);
}
