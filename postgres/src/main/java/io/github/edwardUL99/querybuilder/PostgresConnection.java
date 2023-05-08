package io.github.edwardUL99.querybuilder;

import io.github.edwardUL99.querybuilder.query.PostgresQuery;
import io.github.edwardUL99.querybuilder.query.Query;
import io.github.edwardUL99.querybuilder.utils.Scannable;

/**
 * The connection implementation for Postgres
 */
@Scannable
public class PostgresConnection extends BaseConnection {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialise", e);
        }
    }

    /**
     * Create the MySql connection
     * @param url the jdbc url
     * @param username database username
     * @param password database password
     * @param pooled true to pool the connection, false to not
     */
    public PostgresConnection(String url, String username, String password, boolean pooled) throws Exception {
        super(url, username, password, pooled);
    }

    @Override
    protected String getJdbcName() {
        return "postgresql";
    }

    @Override
    public Query createQuery() {
        return new PostgresQuery();
    }
}
