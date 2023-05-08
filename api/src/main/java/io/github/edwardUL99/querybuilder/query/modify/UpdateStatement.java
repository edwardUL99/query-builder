package io.github.edwardUL99.querybuilder.query.modify;

import io.github.edwardUL99.querybuilder.query.Clause;
import io.github.edwardUL99.querybuilder.query.QueryObject;

import java.util.Map;

/**
 * A statement that allows for the building of an update statement
 */
public interface UpdateStatement extends QueryObject {
    /**
     * Set the fields in the map with key as field name and value as the value to set
     * @param fields the mapping of fields and values
     * @return the update statement
     */
    UpdateStatement setFields(Map<String, String> fields);

    /**
     * Specify the update where clause
     * @param clause the where clause
     * @return the update statement object
     */
    UpdateStatement where(Clause clause);
}
