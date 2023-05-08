package io.github.edwardUL99.querybuilder.utils;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the implementation scanner using the reflections library
 */
public class ReflectionsImplementationScanner implements ImplementationScanner {
    /**
     * The reflections object to search for classes with
     */
    private final Reflections reflections;

    /**
     * Create an instance of the scanner
     */
    public ReflectionsImplementationScanner() {
        this(new Reflections(ClasspathHelper.forJavaClassPath()));
    }

    protected ReflectionsImplementationScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    @Override
    public <T> List<Class<? extends T>> findImplementations(Class<T> type) {
        return reflections.getSubTypesOf(type)
                .stream()
                .filter(c -> c.getAnnotation(Scannable.class) != null).collect(Collectors.toList());
    }
}
