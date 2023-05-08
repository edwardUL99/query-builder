package io.github.edwardUL99.querybuilder.query;

/**
 * Represents a clause of a join ON or query WHERE. After each call to a method that can take a condition,
 * the same condition object can be re-used to avoid constant calls to clause.createCondition.
 */
public interface Clause extends QueryObject {
    /**
     * Add an AND operator without a condition, useful if you want to add a nest before a condition
     * @return the clause object
     */
    Clause and();

    /**
     * Add an AND operator without a condition, useful if you want to add a nest before a condition
     * @return the clause object
     */
    Clause or();

    /**
     * Indicates that the next instruction to add a condition should be nested. If this is called, an equivalent endNest
     * call should be made. Can be called multiple times to nest
     * @return the clause object
     */
    Clause nest();

    /**
     * Indicates that the next condition should be NOT'd
     * @return the clause object
     */
    Clause not();

    /**
     * Un-nest a previous call to nest. Should be called as many times as nest was
     * @return the clause object
     */
    Clause endNest();

    /**
     * Specify an equality condition
     * @param fieldOne the first field to compare
     * @param fieldTwo the second field to compare
     * @return the clause object
     */
    Clause equalsTo(String fieldOne, String fieldTwo);

    /**
     * Specify an inequality condition
     * @param fieldOne the first field to compare
     * @param fieldTwo the second field to compare
     * @return the clause object
     */
    Clause notEqualsTo(String fieldOne, String fieldTwo);

    /**
     * Specify a > condition
     * @param fieldOne the first field to compare
     * @param fieldTwo the second field to compare
     * @return the clause object
     */
    Clause greaterThan(String fieldOne, String fieldTwo);

    /**
     * Specify a >= condition
     * @param fieldOne the first field to compare
     * @param fieldTwo the second field to compare
     * @return the clause object
     */
    Clause greaterEqualsTo(String fieldOne, String fieldTwo);

    /**
     * Specify a < condition
     * @param fieldOne the first field to compare
     * @param fieldTwo the second field to compare
     * @return the clause object
     */
    Clause lessThan(String fieldOne, String fieldTwo);

    /**
     * Specify a > condition
     * @param fieldOne the first field to compare
     * @param fieldTwo the second field to compare
     * @return the clause object
     */
    Clause lessEqualsTo(String fieldOne, String fieldTwo);

    /**
     * Specify an is null condition
     * @param field the field to check if it is null
     * @return the clause object
     */
    Clause isNull(String field);

    /**
     * Specify an is not null condition
     * @param field the field to check if it is not null
     * @return the clause object
     */
    Clause isNotNull(String field);

    /**
     * Specify a LIKE condition
     * @param field the field to check against the pattern
     * @param pattern the like pattern
     * @return the clause object
     */
    Clause like(String field, String pattern);

    /**
     * Specify an IN statement
     * @return the clause object
     */
    Clause in();

    /**
     * Specify a NOT IN statement
     * @return the clause object
     */
    Clause notIn();

    /**
     * Add a sub-query to the clause
     * @param query the sub-query to add
     * @return the clause object
     */
    Clause subQuery(Query query);
}
