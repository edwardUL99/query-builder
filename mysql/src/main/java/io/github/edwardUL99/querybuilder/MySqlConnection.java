package io.github.edwardUL99.querybuilder;

import io.github.edwardUL99.querybuilder.query.MySqlQuery;
import io.github.edwardUL99.querybuilder.query.Query;
import io.github.edwardUL99.querybuilder.utils.Scannable;

/**
 * The connection implementation for MySQL
 */
@Scannable
public class MySqlConnection extends BaseConnection {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialise", e);
        }
    }

    /**
     * Create the MySql connection
     * @param url the jdbc url
     * @param username database username
     * @param password database password
     * @param pooled true to pool, false to not
     */
    public MySqlConnection(String url, String username, String password, boolean pooled) throws Exception {
        super(url, username, password, pooled);
    }

    @Override
    protected String getJdbcName() {
        return "mysql";
    }

    @Override
    public Query createQuery() {
        return new MySqlQuery();
    }
}
