package io.github.edwardUL99.querybuilder.query.modify;

/**
 * The MySQL implementation of the insert statement
 */
public class MySqlUpdateStatement extends BaseUpdateStatement {
    /**
     * Create the update statement with the name of the table being updated
     * @param name the name of the table
     */
    public MySqlUpdateStatement(String name) {
        super(name);
    }
}
