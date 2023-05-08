package io.github.edwardUL99.querybuilder;

import io.github.edwardUL99.querybuilder.utils.ImplementationScanner;
import io.github.edwardUL99.querybuilder.utils.Instantiator;
import io.github.edwardUL99.querybuilder.utils.Instantiators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConnectionFactoryTest {
    private ImplementationScanner mockScanner;
    private Instantiator mockInstantiator;
    private static final String URL = "jdbc:mysql://localhost:3306/db";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @BeforeEach
    public void beforeEach() {
        mockScanner = mock(ImplementationScanner.class);
        mockInstantiator = mock(Instantiator.class);
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void shouldCreateConnection() {
        try (MockedStatic<ImplementationScanner> mockScanner = mockStatic(ImplementationScanner.class);
             MockedStatic<Instantiators> mockInstantiators = mockStatic(Instantiators.class)) {
            Connection mockConnection = mock(Connection.class);

            mockScanner.when(ImplementationScanner::create)
                    .thenReturn(this.mockScanner);
            mockInstantiators.when(Instantiators::constructor)
                    .thenReturn(mockInstantiator);

            Class anyConnection = any();
            when(this.mockScanner.findImplementation(Connection.class))
                    .thenReturn(anyConnection);

            when(mockInstantiator.instantiate(any(), any(), eq(URL), eq(USERNAME), eq(PASSWORD), eq(true)))
                    .thenReturn(mockConnection);

            Connection returned = ConnectionFactory.connect(URL, USERNAME, PASSWORD);

            assertEquals(mockConnection, returned);
            verify(this.mockScanner).findImplementation(Connection.class);
            mockScanner.verify(ImplementationScanner::create);
            mockInstantiators.verify(Instantiators::constructor);
        }
    }
}
