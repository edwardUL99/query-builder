package io.github.edwardUL99.querybuilder.client;

import io.github.edwardUL99.querybuilder.Connection;
import io.github.edwardUL99.querybuilder.ConnectionFactory;
import io.github.edwardUL99.querybuilder.ExecutionResult;
import io.github.edwardUL99.querybuilder.PreparedArgumentSetter;
import io.github.edwardUL99.querybuilder.RunnableQuery;
import io.github.edwardUL99.querybuilder.query.Query;
import io.github.edwardUL99.querybuilder.query.TypeConverter;
import io.github.edwardUL99.querybuilder.query.custom.CustomQuery;
import io.github.edwardUL99.querybuilder.query.modify.InsertStatement;
import io.github.edwardUL99.querybuilder.query.select.Field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static io.github.edwardUL99.querybuilder.query.Clauses.clause;

public class Main {
    private static TypeConverter conv;


    private static List<Person> insertPeople(Connection connection) {
        List<Person> people = new ArrayList<>();

        people.add(new Person(1, "John Doe", 23));
        people.add(new Person(2, "Jane Doe", 32));
        people.add(new Person(3, "Tom Smith", 25));

        Query query = connection.createQuery();
        InsertStatement insert = query.insert("Person")
                .columns("id", "name", "age");

        for (Person person : people) {
            insert.values(conv.number(person.getId()), conv.string(person.getName()), conv.number(person.getAge()))
                    .newRow();
        }

        RunnableQuery.query(query).run(connection);

        return people;
    }

    private static List<Employee> insertEmployees(List<Person> people, Connection connection) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, people.get(0), null));
        employees.add(new Employee(2, people.get(1), employees.get(0)));
        employees.add(new Employee(3, people.get(2), employees.get(0)));

        Query query = connection.createQuery();
        InsertStatement insert = query.insert("Employee")
                .columns("id", "person", "manager");

        for (Employee employee : employees) {
            Employee manager = employee.getManager();
            insert.values(conv.number(employee.getId()), conv.number(employee.getPerson().getId()), (manager == null) ? "NULL":conv.number(manager.getId()))
                    .newRow();
        }

        RunnableQuery.query(query).run(connection);

        return employees;
    }

    private static List<Job> insertJobs(Connection connection) {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(1, "Manager"));
        jobs.add(new Job(2, "Software Engineer"));
        jobs.add(new Job(3, "Product Manager"));

        Query query = connection.createQuery();
        InsertStatement insert = query.insert("Job")
                .columns("id", "name");

        for (Job job : jobs) {
            insert.values(conv.number(job.getId()), conv.string(job.getName()))
                    .newRow();
        }

        RunnableQuery.query(query).run(connection);

        return jobs;
    }

    private static void insertHired(List<Employee> employees, List<Job> jobs, Connection connection) {
        List<Hired> hired = new ArrayList<>();
        hired.add(new Hired(1, employees.get(0), jobs.get(0)));
        hired.add(new Hired(2, employees.get(1), jobs.get(1)));
        hired.add(new Hired(3, employees.get(2), jobs.get(2)));

        Query query = connection.createQuery();
        InsertStatement insert = query.insert("Hired")
                .columns("id", "employee", "job");

        for (Hired hired1 : hired) {
            insert.values(conv.number(hired1.getId()), conv.number(hired1.getEmployee().getId()), conv.number(hired1.getJob().getId()))
                    .newRow();
        }

        RunnableQuery.query(query).run(connection);
    }

    private static Query buildEmployeeViewQuery(Connection connection) {
        Query query = connection.createQuery();
        Field field = query.createField();

        query.selectFrom(new String[]{field.name("Person.name").as("person").build(),
                        field.name("Person.age").as("age").build(), field.name("job.name").as("job").build(),
                        field.name("managerPerson.name").as("manager").build()}, "Person")
                .join("Employee")
                .on(clause()
                        .equalsTo("Person.id", "Employee.person"))
                .result()
                .join("Hired")
                .on(clause()
                        .equalsTo("Hired.employee", "Employee.id"))
                .result()
                .join("Job")
                .alias("job")
                .on(clause()
                        .equalsTo("job.id", "Hired.job"))
                .result()
                .join("Employee")
                .alias("manager")
                .on(clause()
                        .equalsTo("Employee.manager", "manager.id"))
                .result()
                .join("Person")
                .alias("managerPerson")
                .on(clause()
                        .equalsTo("manager.person", "managerPerson.id"))
                .result()
                .where(clause()
                        .equalsTo("Person.name", "?"));

        return query;
    }

    private static void selectEmployeeView(String personName, Connection connection) throws SQLException {
        Query query = buildEmployeeViewQuery(connection);

        System.out.println("Built query: " + query.build());
        PreparedArgumentSetter statementSetter = preparedStatement -> preparedStatement.setString(1, personName);
        RunnableQuery runnable = RunnableQuery.query(query, statementSetter);
        ExecutionResult result = runnable.run(connection);
        ResultSet resultSet = result.getResultSet();

        if (resultSet != null && resultSet.next()) {
            EmployeeView employeeView = new EmployeeView(
                    resultSet.getString("person"),
                    resultSet.getInt("age"),
                    resultSet.getString("job"),
                    resultSet.getString("manager")
            );

            System.out.println(employeeView);
        } else {
            System.out.println("No employee found");
        }
    }

    private static Query buildDirectReportsQuery(Connection connection) {
        Query query = connection.createQuery();
        Field field = query.createField();

        query.selectFrom(new String[]{field.name("p.id").as("id").build(),
                field.name("p.name").as("name").build(), field.name("p.age").as("age").build()}, "Employee")
                .alias("e")
                .join("Person")
                .alias("p")
                .on(clause()
                        .equalsTo("e.person", "p.id"))
                .result()
                .where(clause()
                        .equalsTo("e.manager", "?"));

        return query;
    }

    private static void printPeopleResults(ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            Person person = new Person(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("age")
            );

            System.out.println(person);
        }
    }

    private static void selectDirectReports(Employee manager, Connection connection) throws Exception {
        Query query = buildDirectReportsQuery(connection);
        PreparedArgumentSetter statementSetter = preparedStatement -> preparedStatement.setInt(1, manager.getId());
        RunnableQuery runnableQuery = RunnableQuery.query(query, statementSetter);
        ResultSet resultSet = runnableQuery.run(connection).getResultSet();

        if (resultSet != null) {
            printPeopleResults(resultSet);
        }
    }

    private static void simpleCustomSelect(String personName, Connection connection) throws Exception {
        CustomQuery customQuery = new SimpleCustomQuery("SimpleCustomQuery")
                .withSql("SELECT * FROM Person WHERE name = ?");
        // Arguments will be passed into the ? placeholders in the SQL query in the order of the array
        RunnableQuery runnableQuery = RunnableQuery.custom(customQuery, personName);
        ResultSet resultSet = runnableQuery.run(connection).getResultSet();

        if (resultSet != null) {
            printPeopleResults(resultSet);
        }
    }

    public static void deleteFromDB(Connection connection) {
        Query query = connection.createQuery();
        query.delete("Hired");
        RunnableQuery.query(query).run(connection);

        query = connection.createQuery();
        query.delete("Job");
        RunnableQuery.query(query).run(connection);

        query = connection.createQuery();
        query.delete("Employee")
                .where(clause()
                        .isNotNull("manager"));
        RunnableQuery.query(query).run(connection);

        query = connection.createQuery();
        query.delete("Employee");
        RunnableQuery.query(query).run(connection);

        query = connection.createQuery();
        query.delete("Person");
        RunnableQuery.query(query).run(connection);
    }

    public static void main(String[] args) throws Exception {
        try (Connection connection =
                     ConnectionFactory.connect("localhost:3306/demo", "demo", "demopassword")) {
            conv = connection.getTypeConverter();

            deleteFromDB(connection);
            
            List<Person> people = insertPeople(connection);
            List<Employee> employees = insertEmployees(people, connection);
            List<Job> jobs = insertJobs(connection);
            insertHired(employees, jobs, connection);

            selectEmployeeView("Tom Smith", connection);
            selectEmployeeView("Jane Doe", connection);
            selectDirectReports(employees.get(0), connection);
            simpleCustomSelect("Tom Smith", connection);

            deleteFromDB(connection);
        }
    }
}
