package io.github.edwardUL99.querybuilder.query.modify;

/**
 * The MySQL implementation of the insert statement
 */
public class MySqlInsertStatement extends BaseInsertStatement {
    /**
     * Create the insert statement with the name of the table being inserted into
     * @param name the name of the table
     */
    public MySqlInsertStatement(String name) {
        super(name);
    }
}
