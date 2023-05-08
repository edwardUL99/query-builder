package io.github.edwardUL99.querybuilder.query;

import io.github.edwardUL99.querybuilder.query.modify.BaseDeleteStatement;
import io.github.edwardUL99.querybuilder.query.modify.BaseInsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.BaseUpdateStatement;
import io.github.edwardUL99.querybuilder.query.modify.DeleteStatement;
import io.github.edwardUL99.querybuilder.query.modify.InsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.UpdateStatement;
import io.github.edwardUL99.querybuilder.query.select.BaseField;
import io.github.edwardUL99.querybuilder.query.select.BaseQueryDataSource;
import io.github.edwardUL99.querybuilder.query.select.Field;
import io.github.edwardUL99.querybuilder.query.select.QueryDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * The base query object providing common implementations
 */
public abstract class BaseQuery implements Query {
    /**
     * The builder for the first part of the select query
     */
    protected StringBuilder selectFromBuilder  = new StringBuilder();
    /**
     * The builder for building the part of a select from query after the select clause is made
     */
    protected StringBuilder selectRestBuilder = new StringBuilder();
    /**
     * List of data sources attached to the query if select
     */
    protected final List<BaseQueryDataSource> dataSources = new ArrayList<>();
    /**
     * The insert statement being created
     */
    protected InsertStatement insertStatement;
    /**
     * The update statement being created
     */
    protected UpdateStatement updateStatement;
    /**
     * The delete statement being created
     */
    protected DeleteStatement deleteStatement;
    /**
     * The query that is being built
     */
    private String queryInProgress;

    /**
     * Adds a data-source to the query
     * @param dataSource the data source to add
     */
    public void addDataSource(BaseQueryDataSource dataSource) {
        dataSources.add(dataSource);
    }

    /**
     * Checks if the query is already being built
     */
    protected void checkInProgress() {
        if (queryInProgress != null)
            throw new IllegalStateException("This query is already being used to build a " + queryInProgress +
                    " query");
    }

    /**
     * Create the data source implementation
     * @param name the name of the table being selected from
     * @return the data source implementation
     */
    protected abstract BaseQueryDataSource createDataSource(String name);

    @Override
    public Field createField() {
        return new BaseField();
    }

    private void selectFields(String[] fields) {
        selectFromBuilder.append("SELECT ").append(String.join(", ", fields)).append(' ')
                .append("FROM");
    }

    @Override
    public QueryDataSource selectFrom(String[] fields, String table) {
        queryInProgress = "select";
        selectFields(fields);

        return createDataSource(table);
    }

    @Override
    public QueryDataSource selectFrom(String[] fields, Query subQuery, String subQueryAlias) {
        queryInProgress = "select";
        selectFields(fields);
        selectFromBuilder.append(' ').append('(').append(subQuery.build()).append(')');

        return createDataSource(subQueryAlias);
    }

    /**
     * Create the insert statement implementation
     * @param table the table being inserted into
     * @return the implementation
     */
    protected abstract BaseInsertStatement createInsertStatement(String table);

    @Override
    public InsertStatement insert(String table) {
        checkInProgress();
        queryInProgress = "insert";
        insertStatement = createInsertStatement(table);

        return insertStatement;
    }

    /**
     * Create the updated statement implementation
     * @param table the table being updated
     * @return the implementation
     */
    protected abstract BaseUpdateStatement createUpdateStatement(String table);

    @Override
    public UpdateStatement update(String table) {
        checkInProgress();
        queryInProgress = "update";
        updateStatement = createUpdateStatement(table);

        return updateStatement;
    }

    /**
     * Create the delete statement implementation
     * @param table the table being deleted from
     * @return the implementation
     */
    protected abstract BaseDeleteStatement createDeleteStatement(String table);

    @Override
    public DeleteStatement delete(String table) {
        checkInProgress();
        queryInProgress = "delete";
        deleteStatement = createDeleteStatement(table);

        return deleteStatement;
    }

    @Override
    public String name() {
        if (dataSources.size() > 0) {
            return dataSources.get(0).name();
        } else if (insertStatement != null) {
            return insertStatement.name();
        } else if (updateStatement != null) {
            return updateStatement.name();
        } else if (deleteStatement != null) {
            return deleteStatement.name();
        } else {
            throw new IllegalStateException("Failed to determine query name");
        }
    }

    /**
     * Builds up the select query builder with the data sources if a select query
     */
    protected void buildSelect() {
        for (BaseQueryDataSource source : dataSources)
            selectRestBuilder.append(' ').append(source.build());
    }

    /**
     * Builds the statement if it isn't a select statement
     * @return the built statement
     */
    protected String buildNonSelect() {
        if (insertStatement != null) {
            return insertStatement.build();
        } else if (updateStatement != null) {
            return updateStatement.build();
        } else if (deleteStatement != null) {
            return deleteStatement.build();
        } else {
            return "";
        }
    }

    @Override
    public String build() {
        if (dataSources.size() > 0) {
            buildSelect();
            String built = selectFromBuilder + selectRestBuilder.toString();
            selectRestBuilder = new StringBuilder();

            return built;
        } else {
            return buildNonSelect();
        }
    }
}
