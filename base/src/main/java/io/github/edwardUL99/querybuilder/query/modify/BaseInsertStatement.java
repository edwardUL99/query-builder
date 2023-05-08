package io.github.edwardUL99.querybuilder.query.modify;

import io.github.edwardUL99.querybuilder.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a base statement for inserting values
 */
public class BaseInsertStatement implements InsertStatement {
    /**
     * The name of the insert statement
     */
    protected final String name;
    /**
     * The list of columns to insert into
     */
    protected List<String> columns;
    /**
     * The list of values to insert into
     */
    protected List<List<String>> values;
    /**
     * The current values row
     */
    protected List<String> currentValues;
    /**
     * A query to insert from. Should be a SELECT query
     */
    protected Query query;

    /**
     * Create a base insert statement
     * @param name the name of the statement
     */
    protected BaseInsertStatement(String name) {
        this.name = name;
    }

    /**
     * A hook to add to the query if the implementation supports it
     * @param builder the builder containing the query being built. This is called after all the parts added by this
     *                statement are added
     */
    protected void addAdditional(StringBuilder builder) {}

    @Override
    public String name() {
        return name;
    }

    private void buildValues(StringBuilder builder) {
        builder.append(' ').append("VALUES").append(' ');
        String reduced = values.stream().filter(l -> l.size() > 0).map(l ->
                        String.format("(%s)", String.join(", ", l)))
                .collect(Collectors.joining(", "));

        builder.append(reduced);
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder("INSERT INTO").append(' ').append(name);

        if (columns != null) builder.append(' ').append('(').append(String.join(", ", columns)).append(')');
        if (values != null) buildValues(builder);
        if (query != null) builder.append(' ').append(query.build());

        addAdditional(builder);

        return builder.toString();
    }

    @Override
    public InsertStatement columns(String... columns) {
        this.columns = new ArrayList<>(Arrays.asList(columns));

        return this;
    }

    @Override
    public InsertStatement values(String... values) {
        if (this.values == null) newRow();
        currentValues.addAll(Arrays.asList(values));

        return this;
    }

    @Override
    public InsertStatement newRow() {
        if (this.values == null)
            this.values = new ArrayList<>();

        currentValues = new ArrayList<>();
        this.values.add(currentValues);

        return this;
    }

    @Override
    public InsertStatement query(Query query) {
        this.values = null;
        this.query = query;

        return this;
    }
}
