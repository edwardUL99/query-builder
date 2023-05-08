package io.github.edwardUL99.querybuilder.query.modify;

/**
 * The postgres implementation of the insert statement
 */
public class PostgresUpdateStatement extends BaseUpdateStatement {
    /**
     * Create the update statement with the name of the table being updated
     * @param name the name of the table
     */
    public PostgresUpdateStatement(String name) {
        super(name);
    }
}
