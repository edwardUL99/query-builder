package io.github.edwardUL99.querybuilder.query;

import io.github.edwardUL99.querybuilder.query.Clause;
import io.github.edwardUL99.querybuilder.utils.ImplementationScanner;
import io.github.edwardUL99.querybuilder.utils.Instantiator;
import io.github.edwardUL99.querybuilder.utils.Instantiators;

/**
 * A factory class to create a clause object. You can statically import the method
 */
public final class Clauses {
    /**
     * The implementation clause
     */
    private static final Class<? extends Clause> implementation;
    /**
     * Used to instantiate the clause
     */
    private static final Instantiator instantiator;

    static {
        implementation = ImplementationScanner.create().findImplementation(Clause.class);
        instantiator = Instantiators.constructor();
    }

    /**
     * Create a clause object and return it
     * @return the clause object
     */
    public static Clause clause() {
        return instantiator.instantiate(implementation, new Class<?>[]{});
    }
}
