package io.github.edwardUL99.querybuilder.query.select;

import io.github.edwardUL99.querybuilder.query.Clause;

/**
 * A base ongoing join class to provide common implementation logic
 */
public abstract class BaseOngoingJoin implements OngoingJoin {
    /**
     * The parent data source
     */
    protected final BaseQueryDataSource parent;
    /**
     * The name of the table being joined to
     */
    protected final String name;
    /**
     * An alias for the table name
     */
    protected String alias;
    /**
     * The type of the join
     */
    protected final JoinType joinType;
    /**
     * The resulting data source
     */
    protected BaseQueryDataSource dataSource;
    /**
     * The ON SQL clause
     */
    protected Clause onClause;

    /**
     * Create a base ongoing join object
     * @param parent the parent data source
     * @param name the name of the table being joined to
     * @param joinType the type of join
     */
    protected BaseOngoingJoin(BaseQueryDataSource parent, String name, JoinType joinType) {
        this.parent = parent;
        this.name = name;
        this.joinType = joinType;
    }

    @Override
    public OngoingJoin on(Clause clause) {
        onClause = clause;

        return this;
    }

    @Override
    public OngoingJoin alias(String alias) {
        this.alias = alias;

        return this;
    }

    /**
     * Create the result data source
     * @return the resulting data source
     */
    protected abstract BaseQueryDataSource createResultDataSource();

    @Override
    public QueryDataSource result() {
        if (dataSource == null)
            dataSource = createResultDataSource();

        if (alias != null)
            dataSource = (BaseQueryDataSource) dataSource.alias(alias);

        return dataSource;
    }

    /**
     * Get the join type of the join
     * @return the join type
     */
    public JoinType getJoinType() {
        return joinType;
    }

    /**
     * Get the name of the joined table
     * @return the table name joined to
     */
    public String getName() {
        return name;
    }
}
