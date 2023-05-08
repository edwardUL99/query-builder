package io.github.edwardUL99.querybuilder.query.modify;

/**
 * The MySQL implementation of the delete statement
 */
public class MySqlDeleteStatement extends BaseDeleteStatement {
    /**
     * Create the delete statement with the name of the table being deleted from
     * @param name the name of the table
     */
    public MySqlDeleteStatement(String name) {
        super(name);
    }
}
