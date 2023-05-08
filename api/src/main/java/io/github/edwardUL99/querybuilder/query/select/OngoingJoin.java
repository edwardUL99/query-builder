package io.github.edwardUL99.querybuilder.query.select;

import io.github.edwardUL99.querybuilder.query.Clause;

/**
 * This interface represents an ongoing join which allows the specification of conditions
 */
public interface OngoingJoin {
    /**
     * Defines the join's ON clause
     * @param clause the ON clause
     * @return the ongoing join object
     */
    OngoingJoin on(Clause clause);

    /**
     * The alias for the joined table
     * @param alias the joined table alias
     * @return the ongoing join object
     */
    OngoingJoin alias(String alias);

    /**
     * Get the resulting joined data source. It is created as a singleton instance where the first call to result constructs
     * the data source. Subsequent calls returns the same data source
     * @return the result joined source
     */
    QueryDataSource result();
}
