package io.github.edwardUL99.querybuilder.query.select;

import io.github.edwardUL99.querybuilder.query.BaseQuery;

/**
 * The MySQL implementation of the query data source interface
 */
public class MySqlQueryDataSource extends BaseQueryDataSource {
    /**
     * Create the base data source object
     *
     * @param parent the parent query
     * @param name   the name of the data source
     */
    public MySqlQueryDataSource(BaseQuery parent, String name) {
        super(parent, name);
    }

    @Override
    protected BaseOngoingJoin createOngoingJoin(String name, JoinType joinType) {
        return new MySqlOngoingJoin(this, name, joinType);
    }
}
