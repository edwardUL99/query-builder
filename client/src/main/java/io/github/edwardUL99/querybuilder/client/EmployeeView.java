package io.github.edwardUL99.querybuilder.client;

public class EmployeeView {
    private String name;
    private int age;
    private String job;
    private String manager;

    public EmployeeView(String name, int age, String job, String manager) {
        this.name = name;
        this.age = age;
        this.job = job;
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "EmployeeView{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                ", manager='" + manager + '\'' +
                '}';
    }
}
