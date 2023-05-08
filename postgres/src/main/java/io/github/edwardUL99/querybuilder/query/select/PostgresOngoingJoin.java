package io.github.edwardUL99.querybuilder.query.select;

/**
 * The postgres implementation of the OngoingJoin
 */
public class PostgresOngoingJoin extends BaseOngoingJoin {
    /**
     * Create a base ongoing join object
     *
     * @param parent   the parent data source
     * @param name     the name of the table being joined to
     * @param joinType the type of join
     */
    public PostgresOngoingJoin(BaseQueryDataSource parent, String name, JoinType joinType) {
        super(parent, name, joinType);
    }

    @Override
    protected BaseQueryDataSource createResultDataSource() {
        return new PostgresQueryDataSource(parent.parent, name);
    }
}
