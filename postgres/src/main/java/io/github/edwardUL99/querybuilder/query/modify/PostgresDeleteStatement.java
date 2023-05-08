package io.github.edwardUL99.querybuilder.query.modify;

/**
 * The postgres implementation of the delete statement
 */
public class PostgresDeleteStatement extends BaseDeleteStatement {
    /**
     * Create the delete statement with the name of the table being deleted from
     * @param name the name of the table
     */
    public PostgresDeleteStatement(String name) {
        super(name);
    }
}
