package io.github.edwardUL99.querybuilder;

import io.github.edwardUL99.querybuilder.query.custom.CustomQuery;
import io.github.edwardUL99.querybuilder.query.Query;

/**
 * Used to run a query whether it be pre-defined or a custom one
 */
public final class RunnableQuery {
    /**
     * The query object to run
     */
    private final Query query;

    /**
     * The custom query object to run
     */
    private final CustomQuery customQuery;

    /**
     * A setter which sets variables on a prepared statement
     */
    private final PreparedArgumentSetter preparedArgumentSetter;

    /**
     * List of arguments for the prepared statement
     */
    private final Object[] arguments;

    /**
     * Create a runnable query with either query or custom query
     * @param query the query to run
     * @param customQuery the custom query
     * @param preparedArgumentSetter a setter which sets variables on a prepared statement
     * @param arguments the array of prepared statement arguments
     */
    private RunnableQuery(Query query, CustomQuery customQuery, PreparedArgumentSetter preparedArgumentSetter,
                          Object[] arguments) {
        this.query = query;
        this.customQuery = customQuery;

        if (this.query == null && this.customQuery == null)
            throw new IllegalArgumentException("At least one query or custom query must be provided");

        this.preparedArgumentSetter = preparedArgumentSetter;
        this.arguments = arguments;
    }

    /**
     * Run the query, returning the result set
     * @param connection the connection to run
     * @return the resulting result set
     */
    public ExecutionResult run(Connection connection) {
        String sql = (query != null) ? query.build() : customQuery.build();

        if (preparedArgumentSetter != null) {
            return connection.executePrepared(sql, preparedArgumentSetter);
        } else {
            Object[] arguments = (this.arguments == null) ? new Object[0] : this.arguments;
            return (arguments.length > 0) ? connection.execute(sql, arguments) : connection.execute(sql);
        }
    }

    /**
     * Create a runnable query for the provided query object
     * @param query the query object to run
     * @return the runnable query
     */
    public static RunnableQuery query(Query query) {
        return RunnableQuery.query(query, (PreparedArgumentSetter) null);
    }

    /**
     * Create a runnable query for the provided query object
     * @param query the query object to run
     * @param preparedArgumentSetter a setter which sets variables on a prepared statement
     * @return the runnable query
     */
    public static RunnableQuery query(Query query, PreparedArgumentSetter preparedArgumentSetter) {
        return new RunnableQuery(query, null, preparedArgumentSetter, null);
    }

    /**
     * Create a runnable query for the provided query object
     * @param query the query object to run
     * @param arguments the array of arguments for the query
     * @return the query object
     */
    public static RunnableQuery query(Query query, Object...arguments) {
        return new RunnableQuery(query, null, null, arguments);
    }

    /**
     * Create a runnable query to run the custom query
     * @param custom the custom query
     * @return the runnable query
     */
    public static RunnableQuery custom(CustomQuery custom) {
        return RunnableQuery.custom(custom, (PreparedArgumentSetter) null);
    }

    /**
     * Create a runnable query to run the custom query
     * @param custom the custom query
     * @param preparedArgumentSetter a setter which sets variables on a prepared statement
     * @return the runnable query
     */
    public static RunnableQuery custom(CustomQuery custom, PreparedArgumentSetter preparedArgumentSetter) {
        return new RunnableQuery(null, custom, preparedArgumentSetter, null);
    }

    /**
     * Create a runnable query to run the custom query
     * @param custom the custom query
     * @param arguments the array of arguments for the query
     * @return the runnable query
     */
    public static RunnableQuery custom(CustomQuery custom, Object...arguments) {
        return new RunnableQuery(null, custom, null, arguments);
    }
}
