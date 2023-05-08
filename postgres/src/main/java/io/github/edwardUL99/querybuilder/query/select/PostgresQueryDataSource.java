package io.github.edwardUL99.querybuilder.query.select;

import io.github.edwardUL99.querybuilder.query.BaseQuery;

/**
 * The Postgres implementation of the query data source interface
 */
public class PostgresQueryDataSource extends BaseQueryDataSource {
    /**
     * Create the base data source object
     *
     * @param parent the parent query
     * @param name   the name of the data source
     */
    public PostgresQueryDataSource(BaseQuery parent, String name) {
        super(parent, name);
    }

    @Override
    protected BaseOngoingJoin createOngoingJoin(String name, JoinType joinType) {
        return new PostgresOngoingJoin(this, name, joinType);
    }
}
