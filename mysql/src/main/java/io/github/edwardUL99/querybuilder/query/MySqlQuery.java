package io.github.edwardUL99.querybuilder.query;

import io.github.edwardUL99.querybuilder.query.modify.BaseDeleteStatement;
import io.github.edwardUL99.querybuilder.query.modify.BaseInsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.BaseUpdateStatement;
import io.github.edwardUL99.querybuilder.query.modify.MySqlDeleteStatement;
import io.github.edwardUL99.querybuilder.query.modify.MySqlInsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.MySqlUpdateStatement;
import io.github.edwardUL99.querybuilder.query.select.BaseQueryDataSource;
import io.github.edwardUL99.querybuilder.query.select.MySqlQueryDataSource;

/**
 * The MySQL implementation of the query
 */
public class MySqlQuery extends BaseQuery {
    @Override
    protected BaseQueryDataSource createDataSource(String name) {
        return new MySqlQueryDataSource(this, name);
    }

    @Override
    protected BaseInsertStatement createInsertStatement(String table) {
        return new MySqlInsertStatement(table);
    }

    @Override
    protected BaseUpdateStatement createUpdateStatement(String table) {
        return new MySqlUpdateStatement(table);
    }

    @Override
    protected BaseDeleteStatement createDeleteStatement(String table) {
        return new MySqlDeleteStatement(table);
    }
}
