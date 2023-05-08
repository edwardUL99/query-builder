package io.github.edwardUL99.querybuilder.query.modify;

import io.github.edwardUL99.querybuilder.query.Clause;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a base update statement
 */
public class BaseUpdateStatement implements UpdateStatement {
    /**
     * The name of the insert statement
     */
    protected final String name;
    /**
     * The field and values to set
     */
    protected Map<String, String> fields;
    /**
     * The where clause determining which rows are updated
     */
    protected Clause where;

    /**
     * Create a base update statement
     * @param name the name of the statement
     */
    protected BaseUpdateStatement(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder("UPDATE");

        if (!name.isEmpty()) builder.append(' ').append(name);

        if (fields != null) {
            String converted = fields.entrySet()
                    .stream().map(e -> String.format("%s = %s", e.getKey(), e.getValue()))
                    .collect(Collectors.joining(", "));
            builder.append(' ').append("SET").append(' ').append(converted);
        }

        if (where != null) builder.append(' ').append("WHERE").append(' ').append(where.build());

        return builder.toString();
    }

    @Override
    public UpdateStatement setFields(Map<String, String> fields) {
        if (!(fields instanceof LinkedHashMap)) throw new IllegalArgumentException("The fields map must be an instance" +
                " of LinkedHashMap");

        this.fields = fields;

        return this;
    }

    @Override
    public UpdateStatement where(Clause clause) {
        this.where = clause;

        return this;
    }
}
