package io.github.edwardUL99.querybuilder.query.custom;

import io.github.edwardUL99.querybuilder.query.QueryObject;

/**
 * This abstract class allows you to create a custom query implementation with the build method returning
 * the built query
 */
public abstract class CustomQuery implements QueryObject {
    /**
     * The name of the query
     */
    protected final String name;

    /**
     * Create a custom query
     * @param name the name of the query
     */
    protected CustomQuery(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
