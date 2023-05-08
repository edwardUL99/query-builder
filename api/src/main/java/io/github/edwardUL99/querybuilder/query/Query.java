package io.github.edwardUL99.querybuilder.query;

import io.github.edwardUL99.querybuilder.query.modify.DeleteStatement;
import io.github.edwardUL99.querybuilder.query.modify.InsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.UpdateStatement;
import io.github.edwardUL99.querybuilder.query.select.Field;
import io.github.edwardUL99.querybuilder.query.select.QueryDataSource;

/**
 * This interface represents the base query and the start of the construction of a SELECT query
 */
public interface Query extends QueryObject {
    /**
     * Create the field instance that is suitable for this query object
     * @return the field instance to work on this query
     */
    Field createField();

    /**
     * Select the specified fields from the given table
     * @param fields the fields to select (these can include fields from a subsequent part of the query after joins)
     * @param table the table name
     * @return the data source of the selected table
     */
    QueryDataSource selectFrom(String[] fields, String table);

    /**
     * Allows you to select the specified fields from a sub-query
     * @param fields the fields to select
     * @param subQuery the sub-query object
     * @param subQueryAlias the alias for the sub-query table
     * @return the resulting data source
     */
    QueryDataSource selectFrom(String[] fields, Query subQuery, String subQueryAlias);

    /**
     * Selects all fields from the given table
     * @param table the table name
     * @return the data source of the selected table
     */
    default QueryDataSource selectFrom(String table) {
        return selectFrom(new String[]{"*"}, table);
    }

    /**
     * Allows you to select all fields from a sub-query
     * @param subQuery the sub-query object
     * @param subQueryAlias the alias for the sub-query table
     * @return the resulting data source
     */
    default QueryDataSource selectFrom(Query subQuery, String subQueryAlias) {
        return selectFrom(new String[]{"*"}, subQuery, subQueryAlias);
    }

    /**
     * Insert into the provided table name
     * @param table the name of the table
     * @return the insert statement builder
     */
    InsertStatement insert(String table);

    /**
     * Update the table specified
     * @param table the name of the table
     * @return the update statement builder
     */
    UpdateStatement update(String table);

    /**
     * Delete from the table specified
     * @param table the name of the table
     * @return the delete statement builder
     */
    DeleteStatement delete(String table);
}
