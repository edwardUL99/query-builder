package io.github.edwardUL99.querybuilder.query.modify;

import java.util.ArrayList;
import java.util.List;

/**
 * The postgres implementation of the insert statement
 */
public class PostgresInsertStatement extends BaseInsertStatement {
    /**
     * An optional returning clause for the insert statement in postgres. Users would need to cast the insert statement
     * to access this
     */
    private List<String> returning;

    /**
     * Create the insert statement with the name of the table being inserted into
     * @param name the name of the table
     */
    public PostgresInsertStatement(String name) {
        super(name);
    }

    /**
     * Add a field to the list of fields to return from the insert statement
     * @param returning the field to return
     * @return the insert statement
     */
    public PostgresInsertStatement returning(String returning) {
        if (this.returning == null)
            this.returning = new ArrayList<>();

        this.returning.add(returning);

        return this;
    }

    @Override
    protected void addAdditional(StringBuilder builder) {
        if (this.returning != null)
            builder.append(' ').append("RETURNING").append(' ').append(String.join(", ", this.returning));
    }
}
