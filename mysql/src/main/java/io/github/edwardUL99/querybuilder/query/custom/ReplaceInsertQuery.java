package io.github.edwardUL99.querybuilder.query.custom;

import io.github.edwardUL99.querybuilder.query.Query;
import io.github.edwardUL99.querybuilder.query.modify.InsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.MySqlInsertStatement;

/**
 * A custom MySQL query builder implementing the REPLACE INTO functionality
 */
public class ReplaceInsertQuery extends CustomQuery implements InsertStatement {
    /**
     * The insert statement being delegated to
     */
    private final InsertStatement insertStatement;

    /**
     * Create the query with the given name
     * @param name the name of the query
     */
    public ReplaceInsertQuery(String name) {
        super(name);
        this.insertStatement = new MySqlInsertStatement(name);
    }

    @Override
    public ReplaceInsertQuery columns(String... columns) {
        insertStatement.columns(columns);

        return this;
    }

    @Override
    public ReplaceInsertQuery values(String... values) {
        insertStatement.values(values);

        return this;
    }

    @Override
    public ReplaceInsertQuery newRow() {
        insertStatement.newRow();

        return this;
    }

    @Override
    public ReplaceInsertQuery query(Query query) {
        insertStatement.query(query);

        return this;
    }

    @Override
    public String build() {
        return insertStatement.build().replace("INSERT INTO", "REPLACE INTO");
    }
}
