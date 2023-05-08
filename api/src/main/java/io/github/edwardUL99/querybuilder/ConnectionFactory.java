package io.github.edwardUL99.querybuilder;

import io.github.edwardUL99.querybuilder.utils.ImplementationScanner;
import io.github.edwardUL99.querybuilder.utils.Instantiator;
import io.github.edwardUL99.querybuilder.utils.Instantiators;

/**
 * A factory for creating a connection to a database
 */
public final class ConnectionFactory {
    /**
     * Connect to the database and return the connection
     * @param url the jdbc URL for the database. You can exclude the jdbc:dbname:// part as that's automatically added on
     * @param username the database username
     * @param password the database password
     * @return the connection object
     */
    public static Connection connect(String url, String username, String password) {
        Class<? extends Connection> implementation = ImplementationScanner.create()
                .findImplementation(Connection.class);
        Instantiator instantiator = Instantiators.constructor();

        return instantiator.instantiate(implementation,
                new Class[]{String.class, String.class, String.class, boolean.class},
                url,
                username,
                password,
                true
        );
    }
}
