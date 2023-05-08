package io.github.edwardUL99.querybuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * An object that takes a prepared statement and sets the argument variable values on it
 */
@FunctionalInterface
public interface PreparedArgumentSetter {
    /**
     * Sets the variable values for the arguments provided to the prepared statement
     * @param preparedStatement the statement to set variables on
     * @throws SQLException if an error occurs
     */
    void setVariables(PreparedStatement preparedStatement) throws SQLException;
}
