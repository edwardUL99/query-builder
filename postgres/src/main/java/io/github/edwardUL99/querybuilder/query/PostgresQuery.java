package io.github.edwardUL99.querybuilder.query;

import io.github.edwardUL99.querybuilder.query.modify.BaseDeleteStatement;
import io.github.edwardUL99.querybuilder.query.modify.BaseInsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.BaseUpdateStatement;
import io.github.edwardUL99.querybuilder.query.modify.PostgresDeleteStatement;
import io.github.edwardUL99.querybuilder.query.modify.PostgresInsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.PostgresUpdateStatement;
import io.github.edwardUL99.querybuilder.query.select.BaseQueryDataSource;
import io.github.edwardUL99.querybuilder.query.select.PostgresQueryDataSource;

/**
 * The Postgres implementation of the query
 */
public class PostgresQuery extends BaseQuery {
    @Override
    protected BaseQueryDataSource createDataSource(String name) {
        return new PostgresQueryDataSource(this, name);
    }

    @Override
    protected BaseInsertStatement createInsertStatement(String table) {
        return new PostgresInsertStatement(table);
    }

    @Override
    protected BaseUpdateStatement createUpdateStatement(String table) {
        return new PostgresUpdateStatement(table);
    }

    @Override
    protected BaseDeleteStatement createDeleteStatement(String table) {
        return new PostgresDeleteStatement(table);
    }
}
