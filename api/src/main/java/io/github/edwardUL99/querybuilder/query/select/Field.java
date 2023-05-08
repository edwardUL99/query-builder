package io.github.edwardUL99.querybuilder.query.select;

/**
 * Represents a field selected in the select clause
 */
public interface Field {
    /**
     * Specify the name of the field
     * @param name the field name
     * @return the field instance
     */
    Field name(String name);

    /**
     * Specify an alias for the field
     * @param as the alias for the field
     * @return the field instance
     */
    Field as(String as);

    /**
     * Build the field string
     * After being built, you can reuse the same field object to specify a new field
     * @return the field as a String
     */
    String build();
}
