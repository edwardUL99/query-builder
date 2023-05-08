package io.github.edwardUL99.querybuilder;

import io.github.edwardUL99.querybuilder.query.Query;
import io.github.edwardUL99.querybuilder.query.TypeConverter;

/**
 * Represents a connection to a database
 */
public interface Connection extends AutoCloseable {
    /**
     * Create a query for this connection
     * @return the query that will work on this connection
     */
    Query createQuery();

    /**
     * Execute the provided SQL returning the result set
     * @param sql the SQL to run
     * @return the result of the execution
     */
    ExecutionResult execute(String sql);

    /**
     * Executes a prepared statement, passing the statement through the provided consumer to set the variables before
     * execution
     * @param sql the SQL query with prepared statement placeholders
     * @param preparedArgumentSetter the consumer to set the variables
     * @return the result of the execution
     */
    ExecutionResult executePrepared(String sql, PreparedArgumentSetter preparedArgumentSetter);

    /**
     * Executes an SQL statement with the provided list of arguments. This method, by default, calls
     * executePrepared with the arguments array being set with index (i + 1) with i being the argument position
     * @param sql the SQL query. If not a prepared statement, no arguments should be provided
     * @param arguments the list of prepared statement arguments, all elements must not be null
     * @return the result of the execution
     */
    default ExecutionResult execute(String sql, Object...arguments) {
        return executePrepared(sql, stmt -> {
            for (int i = 0; i < arguments.length; i++) {
                Object arg = arguments[i];

                if (arg == null) throw new IllegalArgumentException("Arguments must not be null");

                stmt.setObject(i + 1, arg);
            }
        });
    }

    /**
     * Execute the runnable query object
     * @param runnableQuery the query object to run
     * @return the execution result
     */
    default ExecutionResult executeRunnableQuery(RunnableQuery runnableQuery) {
        return runnableQuery.run(this);
    }

    /**
     * Get the type converter to convert types to strings suitable for this database connection
     * @return the suitable type converter
     */
    TypeConverter getTypeConverter();

    /**
     * Determines if the connection is still valid
     * @return true if valid, false if not
     */
    boolean isValid();
}
