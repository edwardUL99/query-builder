package io.github.edwardUL99.querybuilder.query.modify;

import io.github.edwardUL99.querybuilder.query.Clause;

/**
 * A base implementation of the delete statement
 */
public class BaseDeleteStatement implements DeleteStatement {
    /**
     * The name of the table being modified
     */
    private final String name;
    /**
     * The where clause determining what is deleted
     */
    protected Clause where;

    /**
     * Create a base delete statement
     * @param name the name of the statement
     */
    protected BaseDeleteStatement(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder("DELETE FROM").append(' ').append(name);

        if (where != null) builder.append(' ').append("WHERE").append(' ').append(where.build());

        return builder.toString();
    }

    @Override
    public DeleteStatement where(Clause clause) {
        this.where = clause;

        return this;
    }
}
