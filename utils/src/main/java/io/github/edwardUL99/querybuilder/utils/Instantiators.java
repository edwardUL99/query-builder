package io.github.edwardUL99.querybuilder.utils;

/**
 * A factory class for instantiation of objects
 */
public final class Instantiators {
    /**
     * Create an instantiator that uses constructors
     * @return the instantiator instance
     */
    public static Instantiator constructor() {
        return new ConstructorInstantiator();
    }

    /**
     * Create an instantiator that instantiates using a static method
     * @param name the name of the method
     * @param returnType the expected method return type
     * @return the instantiator instance
     */
    public static Instantiator method(String name, Class<?> returnType) {
        return new MethodInstantiator(name, returnType);
    }
}
