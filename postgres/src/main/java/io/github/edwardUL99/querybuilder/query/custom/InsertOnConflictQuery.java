package io.github.edwardUL99.querybuilder.query.custom;

import io.github.edwardUL99.querybuilder.query.Query;
import io.github.edwardUL99.querybuilder.query.modify.InsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.PostgresInsertStatement;
import io.github.edwardUL99.querybuilder.query.modify.PostgresUpdateStatement;
import io.github.edwardUL99.querybuilder.query.modify.UpdateStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * A custom query builder implementing the Insert on conflict upsert Postgres condition
 */
public class InsertOnConflictQuery extends CustomQuery implements InsertStatement {
    /**
     * The insert statement being delegated to
     */
    private final InsertStatement insertStatement;
    /**
     * The on conflict builder if specified
     */
    private ConflictBuilder conflictBuilder;

    /**
     * Create the query
     * @param name the name of the table
     */
    public InsertOnConflictQuery(String name) {
        super(name);
        this.insertStatement = new PostgresInsertStatement(name);
    }

    @Override
    public InsertOnConflictQuery columns(String... columns) {
        insertStatement.columns(columns);

        return this;
    }

    @Override
    public InsertOnConflictQuery values(String... values) {
        insertStatement.values(values);

        return this;
    }

    @Override
    public InsertStatement newRow() {
        insertStatement.newRow();

        return this;
    }

    @Override
    public InsertOnConflictQuery query(Query query) {
        insertStatement.query(query);

        return this;
    }

    /**
     * Begins the building process of an on conflict clause
     * @return the builder for the on conflict clause. It is stored in this query to be built when build is called
     */
    public ConflictBuilder onConflict() {
        conflictBuilder = new ConflictBuilder();

        return conflictBuilder;
    }

    @Override
    public String build() {
        StringBuilder built = new StringBuilder(insertStatement.build());
        int replacingIndex = built.indexOf("REPLACING");
        if (replacingIndex > -1) built.delete(replacingIndex - 1, built.length() - 1);

        if (conflictBuilder != null) built.append(' ').append(conflictBuilder.build());

        return null;
    }

    /**
     * A builder for building the on conflict part of the statement
     */
    public class ConflictBuilder {
        /**
         * Specifies if an ON CONSTRAINT clause is specified
         */
        private String constraint;
        /**
         * List of conflict column names
         */
        private final List<String> columns;
        /**
         * The do update clause if specified
         */
        private UpdateStatement doUpdate;
        /**
         * Indicates if nothing should be done
         */
        private boolean doNothing;
        /**
         * An index predicate as per the Postgres spec
         */
        private String indexPredicate;

        private ConflictBuilder() {
            this.columns = new ArrayList<>();
        }

        /**
         * Specify an on constraint clause with the provided constraint name
         * @param constraintName the constraint name
         * @return the builder instance
         */
        public ConflictBuilder onConstraint(String constraintName) {
            constraint = constraintName;
            columns.clear();

            return this;
        }

        /**
         * Specify a list of on conflict columns
         * @param columns the list of on conflict columns
         * @return the builder instance
         */
        public ConflictBuilder onConflict(String...columns) {
            constraint = null;
            this.columns.addAll(Arrays.asList(columns));

            return this;
        }

        /**
         * Specify a DO UPDATE clause. The passed builder will accept an update statement which will build up the SET
         * values
         * @param builder the function that will build up the update statement passed into it
         * @return the builder instance
         */
        public ConflictBuilder doUpdate(Consumer<UpdateStatement> builder) {
            doNothing = false;
            doUpdate = new PostgresUpdateStatement("");
            builder.accept(doUpdate);

            return this;
        }

        /**
         * Specify that nothing should be done on conflict
         * @return the builder instance
         */
        public ConflictBuilder doNothing() {
            doUpdate = null;
            doNothing = true;

            return this;
        }

        /**
         * Specify an index predicate as per the Postgres spec at
         * <a href="https://www.postgresql.org/docs/current/sql-insert.html">Insert Statement</a>
         * @param indexPredicate the predicate string
         * @return the builder instance
         */
        public ConflictBuilder indexPredicate(String indexPredicate) {
            this.indexPredicate = indexPredicate;

            return this;
        }

        /**
         * Builds the on conflict clause
         * @return built clause
         */
        public String build() {
            StringBuilder builder = new StringBuilder("ON CONFLICT").append(' ');

            if (constraint != null) builder.append("ON CONSTRAINT").append(' ').append(constraint);
            else if (columns.size() > 0) builder.append('(').append(String.join(",", columns)).append(')');

            if (indexPredicate != null) builder.append(' ').append("WHERE").append(' ').append(indexPredicate);

            if (doUpdate != null) builder.append(' ').append("DO").append(' ').append(doUpdate.build());
            else if (doNothing) builder.append(' ').append("DO NOTHING");

            return builder.toString();
        }

        /**
         * Retrieve the query instance
         * @return query instance
         */
        public InsertOnConflictQuery query() {
            return InsertOnConflictQuery.this;
        }
    }
}
