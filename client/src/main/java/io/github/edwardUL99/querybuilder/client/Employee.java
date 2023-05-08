package io.github.edwardUL99.querybuilder.client;

public class Employee {
    private int id;
    private Person person;
    private Employee manager;

    public Employee(int id, Person person, Employee manager) {
        this.id = id;
        this.person = person;
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}
