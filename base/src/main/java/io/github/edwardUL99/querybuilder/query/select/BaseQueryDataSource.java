package io.github.edwardUL99.querybuilder.query.select;

import io.github.edwardUL99.querybuilder.query.BaseQuery;
import io.github.edwardUL99.querybuilder.query.Clause;
import io.github.edwardUL99.querybuilder.query.Query;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a base query data source object that can be overridden by implementations
 */
public abstract class BaseQueryDataSource implements QueryDataSource {
    /**
     * The parent query object
     */
    protected final BaseQuery parent;
    /**
     * If this data source is joined with another data source, this field will contain the join object
     */
    protected BaseOngoingJoin join;
    /**
     * The table alias
     */
    protected String alias;
    /**
     * The name of the data source
     */
    protected final String name;
    /**
     * The data source's where clause
     */
    protected Clause whereClause;
    /**
     * The limit on the results
     */
    protected int limit = -1;
    /**
     * The list of order by fields
     */
    protected final List<OrderByField> orderByFields;
    /**
     * The list of group by fields
     */
    protected final List<String> groupByFields;
    /**
     * The having clause used with group by
     */
    protected Clause havingClause;

    /**
     * Create the base data source object
     * @param parent the parent query
     * @param name the name of the data source
     */
    protected BaseQueryDataSource(BaseQuery parent, String name) {
        this.parent = parent;
        this.parent.addDataSource(this);
        this.name = name;
        this.orderByFields = new ArrayList<>();
        this.groupByFields = new ArrayList<>();
    }

    @Override
    public Query getParent() {
        return parent;
    }

    /**
     * Create the implementation of the ongoing join object
     * @param name the name of the table to join to
     * @param joinType the type of the join
     * @return the ongoing join object
     */
    protected abstract BaseOngoingJoin createOngoingJoin(String name, JoinType joinType);

    @Override
    public OngoingJoin join(String name, JoinType joinType) {
        BaseOngoingJoin join = createOngoingJoin(name, joinType);
        ((BaseQueryDataSource)join.result()).join = join;

        return join;
    }

    @Override
    public QueryDataSource alias(String alias) {
        this.alias = alias;

        return this;
    }

    @Override
    public QueryDataSource where(Clause clause) {
        whereClause = clause;

        return this;
    }

    @Override
    public QueryDataSource limit(int limit) {
        this.limit = limit;

        return this;
    }

    @Override
    public QueryDataSource orderBy(String field, OrderBy type) {
        orderByFields.add(new OrderByField(field, type));

        return this;
    }

    @Override
    public QueryDataSource groupBy(String field) {
        groupByFields.add(field);

        return this;
    }

    @Override
    public QueryDataSource having(Clause clause) {
        havingClause = clause;

        return this;
    }

    @Override
    public String name() {
        return name;
    }

    protected boolean buildJoin(StringBuilder builder) {
        if (join != null) {
            builder.append(join.getJoinType().name())
                    .append(' ')
                    .append("JOIN")
                    .append(' ');

            builder.append(join.name);

            if (alias != null) builder.append(' ').append(alias);
            if (join.onClause != null) builder.append(' ').append("ON").append(' ').append(join.onClause.build());

            return true;
        }

        return false;
    }

    protected void buildName(StringBuilder builder) {
        builder.append(name);

        if (alias != null) builder.append(' ').append(alias);
    }

    protected void buildWhere(StringBuilder builder) {
        if (whereClause != null) {
            builder.append(' ').append("WHERE").append(' ').append(whereClause.build());
        }
    }

    protected Map<OrderBy, String> mapOrderBy() {
        Map<OrderBy, String> map = new LinkedHashMap<>();
        map.put(OrderBy.ASCENDING, "ASC");
        map.put(OrderBy.DESCENDING, "DESC");

        return map;
    }

    protected void buildOrderBy(StringBuilder builder) {
        Map<OrderBy, String> orderByMap = mapOrderBy();

        if (orderByFields.size() > 0) {
            String joined = orderByFields.stream()
                    .map(f -> String.format("%s %s", f.field, orderByMap.getOrDefault(f.orderBy, f.orderBy.toString())))
                    .collect(Collectors.joining(", "));

            builder.append(' ').append("ORDER BY").append(' ').append(joined);
        }
    }

    protected void buildGroupBy(StringBuilder builder) {
        if (groupByFields.size() > 0) {
            builder.append(' ').append("GROUP BY").append(' ')
                    .append(String.join(", ", groupByFields));
        }
    }

    protected void buildHaving(StringBuilder builder) {
        if (havingClause != null) {
            builder.append(' ').append("HAVING").append(' ')
                    .append(havingClause.build());
        }
    }

    protected void buildLimit(StringBuilder builder) {
        if (limit != -1) {
            builder.append(' ').append("LIMIT").append(' ').append(limit);
        }
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        if (!buildJoin(builder)) buildName(builder);

        buildWhere(builder);
        buildOrderBy(builder);
        buildGroupBy(builder);
        buildHaving(builder);
        buildLimit(builder);

        return builder.toString();
    }

    /**
     * A field defining an order by clause
     */
    protected static class OrderByField {
        /**
         * The name of the field
         */
        protected final String field;
        /**
         * The type of ordering to implement
         */
        protected final OrderBy orderBy;

        /**
         * Create the object
         * @param field the field name
         * @param orderBy the type of ordering
         */
        public OrderByField(String field, OrderBy orderBy) {
            this.field = field;
            this.orderBy = orderBy;
        }
    }
}
