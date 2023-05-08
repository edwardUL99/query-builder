package io.github.edwardUL99.querybuilder.query.modify;

import io.github.edwardUL99.querybuilder.query.QueryObject;
import io.github.edwardUL99.querybuilder.query.Query;

/**
 * Represents an INSERT statement being built
 */
public interface InsertStatement extends QueryObject {
    /**
     * Select the columns to insert
     * @param columns the columns to insert
     * @return the statement object
     */
    InsertStatement columns(String...columns);

    /**
     * The value list to insert. The number of values provided must match the number of columns
     * @param values the list of values
     * @return the statement object
     */
    InsertStatement values(String...values);

    /**
     * Specifies that the next call to values should be a new row to insert
     * @return the statement object
     */
    InsertStatement newRow();

    /**
     * Insert values into the table using a SELECT query
     * @param query the select query to insert with
     * @return the insert statement
     */
    InsertStatement query(Query query);
}
