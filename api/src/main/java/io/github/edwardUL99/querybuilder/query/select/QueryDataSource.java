package io.github.edwardUL99.querybuilder.query.select;

import io.github.edwardUL99.querybuilder.query.Clause;
import io.github.edwardUL99.querybuilder.query.QueryObject;

/**
 * This interface represents a datasource added to a query. A datasource is typically a table
 */
public interface QueryDataSource extends QueryObject {
    /**
     * Get the parent object that created this data source
     * @return the parent object
     */
    QueryObject getParent();

    /**
     * Create a join to a table using an inner join as default
     * @param table the name of the table to join to
     * @return the ongoing join
     */
    default OngoingJoin join(String table) {
        return join(table, JoinType.INNER);
    }

    /**
     * Create a join to a table
     * @param table the name of the table to join to
     * @param joinType the type of join
     * @return the ongoing join
     */
    OngoingJoin join(String table, JoinType joinType);

    /**
     * Specify an alias for the table
     * @param alias the table's alias
     * @return the data source
     */
    QueryDataSource alias(String alias);

    /**
     * Specify a where clause on the data source. This should only be added to the final data source
     * @param clause the where clause
     * @return the current data source
     */
    QueryDataSource where(Clause clause);

    /**
     * Specify a limit clause on the data source. This should only be added to the final data source
     * @param limit the number of results to limit to
     * @return the current data source
     */
    QueryDataSource limit(int limit);

    /**
     * Order the results by the specified field and type. If already called, these will be added to the list of ordered
     * fields. This should only be added to the final data source
     * @param field the field to order by
     * @param type the type of the ordering
     * @return the current data source
     */
    QueryDataSource orderBy(String field, OrderBy type);

    /**
     * Order by with the given field and default ascending order
     * @param field the name of the field to order by
     * @return the current data source
     */
    default QueryDataSource orderBy(String field) {
        return orderBy(field, OrderBy.ASCENDING);
    }

    /**
     * Group the results by the specified field. If already called, the fields will be added to the list of group by.
     * This should only be added to the final data source
     * @param field the field to group by
     * @return the current data source
     */
    QueryDataSource groupBy(String field);

    /**
     * Add a having clause to the datasource. This should only be added to the final data source
     * @param clause the having clause
     * @return the current data source
     */
    QueryDataSource having(Clause clause);
}
