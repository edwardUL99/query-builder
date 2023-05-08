package io.github.edwardUL99.querybuilder.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO keep writing tests for utils package and other packages. base package should provide common base testing classes with
// methods to call from implementing tests. i.e. base will have no tests run, (just providing base test classes). The implementing packages will have tests implementing these
public class MethodInstantiatorTest {
    @Test
    public void shouldInstantiate() {
        MethodInstantiator instantiator = new MethodInstantiator("instantiate", Object.class);

        Type instantiated = instantiator.instantiate(Type.class, new Class<?>[]{String.class}, "Hello World");

        assertEquals("Hello World", instantiated.s);
    }

    @Test
    public void shouldThrowError() {
        MethodInstantiator instantiator = new MethodInstantiator("instantiate", Object.class);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                instantiator.instantiate(Type.class, new Class<?>[]{}));
        assertTrue(ex.getMessage().contains("Failed to instantiate"));

        MethodInstantiator instantiator1 = new MethodInstantiator("invalid", Type.class);
        ex = assertThrows(IllegalStateException.class, () ->
                instantiator1.instantiate(Type.class, new Class<?>[]{String.class}, "hello"));
        assertTrue(ex.getMessage().contains("The method's return type must be assignable to:"));

        MethodInstantiator instantiator2 = new MethodInstantiator("nonStatic", Type.class);
        ex = assertThrows(IllegalStateException.class, () ->
                instantiator2.instantiate(Type.class, new Class<?>[]{String.class}, "hello"));
        assertTrue(ex.getMessage().contains("The method must be static"));
    }
}
