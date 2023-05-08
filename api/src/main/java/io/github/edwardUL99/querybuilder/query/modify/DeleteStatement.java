package io.github.edwardUL99.querybuilder.query.modify;

import io.github.edwardUL99.querybuilder.query.Clause;
import io.github.edwardUL99.querybuilder.query.QueryObject;

/**
 * Represents an object to build a delete statement
 */
public interface DeleteStatement extends QueryObject {
    /**
     * Specify the where clause for the deletion
     * @param clause the where clause
     * @return the statement object
     */
    DeleteStatement where(Clause clause);
}
