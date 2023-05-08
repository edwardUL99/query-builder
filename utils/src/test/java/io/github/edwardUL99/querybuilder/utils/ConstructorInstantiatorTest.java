package io.github.edwardUL99.querybuilder.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorInstantiatorTest {
    private ConstructorInstantiator instantiator;

    @BeforeEach
    public void beforeEach() {
        instantiator = new ConstructorInstantiator();
    }

    @Test
    public void shouldInstantiateClass() {
        Type instantiated = instantiator.instantiate(Type.class, new Class<?>[]{String.class}, "Hello World");

        assertEquals("Hello World", instantiated.s);
    }

    @Test
    public void shouldThrowException() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                instantiator.instantiate(Type.class, new Class<?>[]{}));
        assertTrue(ex.getMessage().contains("Failed to instantiate"));
    }
}
