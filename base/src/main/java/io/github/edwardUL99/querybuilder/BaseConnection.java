package io.github.edwardUL99.querybuilder;

import io.github.edwardUL99.querybuilder.query.BaseTypeConverter;
import io.github.edwardUL99.querybuilder.query.TypeConverter;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The base SQL connection
 */
public abstract class BaseConnection implements Connection {
    /**
     * The connection to the SQL database
     */
    protected final java.sql.Connection sqlConnection;

    /**
     * Create the MySql connection
     * @param url the jdbc url
     * @param username database username
     * @param password database password
     */
    protected BaseConnection(String url, String username, String password, boolean pooled) throws Exception {
        sqlConnection = DriverManager.getConnection(createURL(url), username, password);
    }

    private String createURL(String url) {
        String prefix = "jdbc:" + getJdbcName() + "://";

        return (url.startsWith(prefix)) ? url : prefix + url;
    }

    /**
     * Get the string used in jdbc:[jdbc-name]//
     * @return JDBC driver name
     */
    protected abstract String getJdbcName();

    private ExecutionResult fromStatementResult(Statement statement, StatementExecution execution) throws SQLException {
        boolean hasResultSet = execution.execute(statement);
        ResultSet generatedKeys;

        try {
            generatedKeys = statement.getGeneratedKeys();
        } catch (SQLException ignored) {
            generatedKeys = null;
        }

        return new ExecutionResult(
                (hasResultSet) ? statement.getResultSet() : null,
                (hasResultSet) ? -1 : statement.getUpdateCount(),
                generatedKeys
        );
    }

    @Override
    public ExecutionResult execute(String sql) {
        try {
            Statement statement = sqlConnection.createStatement();

            return fromStatementResult(statement, s -> s.execute(sql, Statement.RETURN_GENERATED_KEYS));
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to execute SQL", ex);
        }
    }

    @Override
    public ExecutionResult executePrepared(String sql, PreparedArgumentSetter preparedArgumentSetter) {
        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedArgumentSetter.setVariables(preparedStatement);

            return fromStatementResult(preparedStatement, s -> ((PreparedStatement)s).execute());
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to execute SQL", ex);
        }
    }

    @Override
    public void close() {
        try {
            sqlConnection.close();
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to close", exception);
        }
    }

    @Override
    public TypeConverter getTypeConverter() {
        return new BaseTypeConverter();
    }

    @Override
    public boolean isValid() {
        try {
            return !this.sqlConnection.isClosed() && this.sqlConnection.isValid(5);
        } catch (SQLException ignored) {
            return false;
        }
    }

    private interface StatementExecution {
        boolean execute(Statement statement) throws SQLException;
    }
}
