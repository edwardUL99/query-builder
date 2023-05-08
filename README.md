query-builder
==
This project provides an SQL query builder API for Java which allows for easy building
and execution of SQL queries programmatically. It has been designed to theoretically support many
SQL dialects, through the use of plugins/modules, i.e. you import the api maven module and the implementation
maven module that you wish to use. Currently, the following dialects are supported:
- MySQL - Dependency is `io.github.edwardUL99.querybuilder:mysql:<version>`
- PostgreSQL - Dependency is `io.github.edwardUL99.querybuilder:postgres:<version>`

More Maven modules can theoretically be created to add support for different dialects.

To select the implementation that you want to use, you import the api module (`io.github.edwardUL99.querybuilder:api:<version>`)
and your implementation module. You can only have one imported at a time.

## Caveat
Due to the support for varying, the base api provides the ability to build SQL queries as dialect-agnostic as possible.
Therefore, only the core features common between all SQL dialects are supported. Implementations may contain methods which
allow building SQL constructs specific to that construct, but you have to use downcasting in the following format. The
builder methods in the api return the base interfaces for the intermediate SQL builder objects, for example,
the `Query` class represents the query being built. However, to downcast, based on the implementation you have imported,
the class name would be `<Implementation>Query`.

For example, to downcast for MySQL, you would use `MySqlQuery`, and for PostgreSQL, you would use
`PostgresQuery`. However, this has its downsides of coupling you to the specific implementation. Take the following scenario:
1. You are creating a project which relies on a PostgreSQL database
2. You are using the implementation-specific methods, so you have casts to the postgres specific
implementations throughout the project.
3. Suppose the requirement changes to use a MySQL database. 
4. The logical solution is to swap the postgres module for the mysql module
5. However, you now have compilation errors due to the postgres classes no longer existed.
6. You have to change the casts and methods called everywhere they are used in the codebase to satisfy mysql.

Had you relied solely on the base API, theoretically, the change should require no/minimal changes. Sometimes it is impossible
to avoid casting, either because you want to use custom queries provided in the implementation packages (custom queries specific to that
dialect), or your query requires you to use dialect specific constructs. However, the number of times you have to downcast
should be tracked and minimised to make any changes easier and to reduce coupling as much as possible. This way, the change
to implementation would result in a smaller explosion throughout the code base

## Usage
To see usage examples, see the `client` module provided in this repository which provides a small sample
of an employee management system. Note that this example does not make downcasts anywhere.

The sample uses a `MySQL` for demonstration purposes. On Linux, once you have a MySQL database setup, run the following from the root of the client module:
1. Run `sudo mysql`
2. `CREATE USER 'demo'@'localhost' IDENTIFIED BY PASSWORD 'demopassword'`
3. `CREATE DATABASE demo`
4. `GRANT ALL PRIVILEGES ON demo.* TO 'demo'@'localhost' WITH GRANT OPTION`
5. `FLUSH PRIVILEGES`
6. `exit`
7. Run `mysql -u demo --password=demopassword demo < src/main/resources/schema.sql`

Now the database is setup to run the client samples.

## Custom queries
To circumvent the need for downcasting (or to at least reduce its use), you can write your own custom queries by 2 means:
1. Create a query using a string and use the `execute(String sql)` method on the connection or,
2. Create your own subclass of the `CustomQuery` abstract class in the API package. This can be passed to a `RunnableQuery` to execute the custom query. 
The `build` method should return the built SQL.

Typically, custom query implementations should follow the `fluent builder` pattern like the existing query builder objects
in the base api. However, as long as the build method returns correct SQL, it does not matter how the builder is constructed.

While using these custom queries can still bind you to a specific SQL implementation, it reduces the likelihood of requiring
to downcast for the sake of one query that is not dialect agnostic. Long story short, try to keep your
queries as dialect-agnostic as possible, and instead of downcasting, try to (sparingly) use custom queries.

The implementation modules may provide some custom queries out of the box relevant to that dialect. For example:
1. The `mysql` implementation has the `ReplaceInsertQuery` custom query which implements the MySql
`REPLACE INTO` construct
2. The `postgres` implementation has the `InsertOnConflictQuery` custom query which implements the Postgresql
`INSERT INTO .... ON CONFLICT ...` construct