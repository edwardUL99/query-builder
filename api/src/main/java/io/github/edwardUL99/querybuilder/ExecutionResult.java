package io.github.edwardUL99.querybuilder;

import java.sql.ResultSet;

/**
 * Represents the result of an SQL execution
 */
public class ExecutionResult {
    /**
     * The result set if one exists
     */
    private final ResultSet resultSet;
    /**
     * The update count if one is available
     */
    private final int updateCount;
    /**
     * The generated keys from the query if supported
     */
    private final ResultSet generatedKeys;

    /**
     * Create a result
     * @param resultSet the result set if it exists
     * @param updateCount the update count if applicable
     * @param generatedKeys result set identifying generated keys if supported
     */
    ExecutionResult(ResultSet resultSet, int updateCount, ResultSet generatedKeys) {
        this.resultSet = resultSet;
        this.updateCount = updateCount;
        this.generatedKeys = generatedKeys;
    }

    /**
     * Get the result set if it exists
     * @return result set if exists
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Get the update count
     * @return update count if applicable
     */
    public int getUpdateCount() {
        return updateCount;
    }

    /**
     * Get the result set containing any generated keys if they exist
     * @return the result set containing keys
     */
    public ResultSet getGeneratedKeys() {
        return generatedKeys;
    }
}
