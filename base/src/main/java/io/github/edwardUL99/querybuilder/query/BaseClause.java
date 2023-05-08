package io.github.edwardUL99.querybuilder.query;

/**
 * Represents a base clause which can be extended depending on implementation
 */
public class BaseClause implements Clause {
    /**
     * The clause in progress
     */
    protected StringBuilder progress = new StringBuilder("(");

    /**
     * Get the matching logical operator for and/or
     * @param andOr true if and, false if or
     * @return the operator
     */
    protected String getLogicalOperator(boolean andOr) {
        return (andOr) ? "AND" : "OR";
    }

    private void buildLogical(boolean andOr) {
        final String operator = getLogicalOperator(andOr);
        progress.append(operator).append(' ');
    }

    @Override
    public Clause and() {
        buildLogical(true);

        return this;
    }

    @Override
    public Clause or() {
        buildLogical(false);

        return this;
    }

    @Override
    public Clause nest() {
        progress.append("(");

        return this;
    }

    @Override
    public Clause not() {
        progress.append('!');

        return this;
    }

    @Override
    public Clause endNest() {
        int end = progress.length() - 1;
        if (Character.isSpaceChar(progress.charAt(end)))
            progress.setCharAt(end, ')');
        else
            progress.append(")");

        return this;
    }

    private void addCondition(String condition) {
        progress.append(condition).append(' ');
    }

    @Override
    public Clause equalsTo(String fieldOne, String fieldTwo) {
        addCondition(String.format("%s = %s", fieldOne, fieldTwo));

        return this;
    }

    @Override
    public Clause notEqualsTo(String fieldOne, String fieldTwo) {
        addCondition(String.format("%s != %s", fieldOne, fieldTwo));

        return this;
    }

    @Override
    public Clause greaterThan(String fieldOne, String fieldTwo) {
        addCondition(String.format("%s > %s", fieldOne, fieldTwo));

        return this;
    }

    @Override
    public Clause greaterEqualsTo(String fieldOne, String fieldTwo) {
        addCondition(String.format("%s >= %s", fieldOne, fieldTwo));

        return this;
    }

    @Override
    public Clause lessThan(String fieldOne, String fieldTwo) {
        addCondition(String.format("%s < %s", fieldOne, fieldTwo));

        return this;
    }

    @Override
    public Clause lessEqualsTo(String fieldOne, String fieldTwo) {
        addCondition(String.format("%s <= %s", fieldOne, fieldTwo));

        return this;
    }

    @Override
    public Clause isNull(String field) {
        addCondition(String.format("%s IS NULL", field));

        return this;
    }

    @Override
    public Clause isNotNull(String field) {
        addCondition(String.format("%s IS NOT NULL", field));

        return this;
    }

    @Override
    public Clause like(String field, String pattern) {
        addCondition(String.format("%s LIKE '%s'", field, pattern));

        return this;
    }

    @Override
    public Clause in() {
        addCondition("IN");

        return this;
    }

    @Override
    public Clause notIn() {
        addCondition("NOT IN");

        return this;
    }

    @Override
    public Clause subQuery(Query query) {
        progress.append('(').append(query.build()).append(')');

        return this;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String build() {
        progress.setCharAt(progress.length() - 1, ')');

        return progress.toString();
    }
}
