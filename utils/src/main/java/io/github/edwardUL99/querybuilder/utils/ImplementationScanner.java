package io.github.edwardUL99.querybuilder.utils;

import java.util.List;

/**
 * Scans for possible implementations on the classpath of a specified parent class
 */
public interface ImplementationScanner {
    /**
     * Find implementations of the provided type
     * @param type the class of the implementation parent
     * @return the list of implementation classes
     */
    <T> List<Class<? extends T>> findImplementations(Class<T> type);

    /**
     * Find a single implementation of the provided type. If more implementations are found, an exception is thrown
     * @param type the type of the implementation
     * @return the found class
     */
    default <T> Class<? extends T> findImplementation(Class<T> type) {
        List<Class<? extends T>> found = findImplementations(type);
        int size = found.size();

        if (size == 1) return found.get(0);
        else if (size == 0) throw new IllegalArgumentException("No implementations for type: " + type.getName() + " found");
        else throw new IllegalStateException("Cannot have multiple implementations of the same type using this method");
    }

    /**
     * Create an instance of the implementation scanner
     * @return the found instance
     */
    static ImplementationScanner create() {
        return new ReflectionsImplementationScanner();
    }
}
