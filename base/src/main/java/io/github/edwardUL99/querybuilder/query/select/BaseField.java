package io.github.edwardUL99.querybuilder.query.select;

/**
 * This class represents a base field instance. It can be directly instantiated and returned from BaseQuery, or else it
 * can be extended by implementation and returned by the implementation's query object
 */
public class BaseField implements Field {
    /**
     * The name of the field
     */
    private String name;
    /**
     * The alias of the field
     */
    private String alias;

    @Override
    public Field name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public Field as(String as) {
        this.alias = as;

        return this;
    }

    @Override
    public String build() {
        String field = name;

        if (alias != null) field += " AS " + alias;

        return field;
    }
}
