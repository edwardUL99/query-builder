package io.github.edwardUL99.querybuilder.client;

import io.github.edwardUL99.querybuilder.query.custom.CustomQuery;

/**
 * This class is just a sample on how to create and run a custom query. It essentially just parrots back the String
 * SQL provided
 */
public class SimpleCustomQuery extends CustomQuery {
    private String sql;

    public SimpleCustomQuery(String name) {
        super(name);
    }

    // Implements the fluent builder pattern to align with the base api
    public SimpleCustomQuery withSql(String sql) {
        this.sql = sql;

        return this;
    }

    @Override
    public String build() {
        return sql;
    }
}
