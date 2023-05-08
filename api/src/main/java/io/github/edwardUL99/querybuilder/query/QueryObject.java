package io.github.edwardUL99.querybuilder.query;

/**
 * Represents a basic "building block" of a query.
 */
public interface QueryObject {
    /**
     * The name of this query object
     * @return the name of the object
     */
    String name();

    /**
     * Build this part of the query to a string. After this is called, the object should be able to be used again
     * for a new query object
     * @return the string representation
     */
    String build();
}
