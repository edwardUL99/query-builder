package io.github.edwardUL99.querybuilder;

import io.github.edwardUL99.querybuilder.query.Query;
import io.github.edwardUL99.querybuilder.query.custom.CustomQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RunnableQueryTest {
    private Connection mockConnection;
    private PreparedArgumentSetter mockStatementSetter;
    private Query mockQuery;
    private CustomQuery mockCustomQuery;
    private ExecutionResult mockResult;
    private static final String SQL = "SELECT * FROM table";

    @BeforeEach
    public void beforeEach() {
        mockConnection = mock(Connection.class);
        mockStatementSetter = mock(PreparedArgumentSetter.class);
        mockQuery = mock(Query.class);
        mockCustomQuery = mock(CustomQuery.class);
        mockResult = mock(ExecutionResult.class);

        when(mockQuery.build())
                .thenReturn(SQL);
        when(mockCustomQuery.build())
                .thenReturn(SQL);
        when(mockConnection.executePrepared(SQL, mockStatementSetter))
                .thenReturn(mockResult);
        when(mockConnection.execute(SQL))
                .thenReturn(mockResult);
    }

    @Test
    public void shouldRunQuery() {
        RunnableQuery runnableQuery = RunnableQuery.query(mockQuery, mockStatementSetter);

        ExecutionResult result = runnableQuery.run(mockConnection);

        assertEquals(mockResult, result);
        verify(mockConnection).executePrepared(SQL, mockStatementSetter);
        verify(mockQuery).build();
        verify(mockCustomQuery, times(0)).build();
    }

    @Test
    public void shouldRunQueryNotPrepared() {
        RunnableQuery runnableQuery = RunnableQuery.query(mockQuery);

        ExecutionResult result = runnableQuery.run(mockConnection);

        assertEquals(mockResult, result);
        verify(mockConnection).execute(SQL);
        verify(mockQuery).build();
        verify(mockCustomQuery, times(0)).build();
    }

    @Test
    public void shouldRunCustomQuery() {
        RunnableQuery runnableQuery = RunnableQuery.custom(mockCustomQuery, mockStatementSetter);

        ExecutionResult result = runnableQuery.run(mockConnection);

        assertEquals(mockResult, result);
        verify(mockConnection).executePrepared(SQL, mockStatementSetter);
        verify(mockCustomQuery).build();
        verify(mockQuery, times(0)).build();
    }

    @Test
    public void shouldRunCustomQueryNotPrepared() {
        RunnableQuery runnableQuery = RunnableQuery.custom(mockCustomQuery);

        ExecutionResult result = runnableQuery.run(mockConnection);

        assertEquals(mockResult, result);
        verify(mockConnection).execute(SQL);
        verify(mockCustomQuery).build();
        verify(mockQuery, times(0)).build();
    }
}
