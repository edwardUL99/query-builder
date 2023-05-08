package io.github.edwardUL99.querybuilder.client;

public class Hired {
    private int id;
    private Employee employee;
    private Job job;

    public Hired(int id, Employee employee, Job job) {
        this.id = id;
        this.employee = employee;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
