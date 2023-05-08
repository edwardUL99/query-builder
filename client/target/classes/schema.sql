CREATE TABLE Person(
    id INTEGER PRIMARY KEY,
    name VARCHAR(255),
    age INTEGER
);

CREATE TABLE Employee(
    id INTEGER PRIMARY KEY,
    person INTEGER,
    manager INTEGER,
    FOREIGN KEY (person) REFERENCES Person(id),
    FOREIGN KEY (manager) REFERENCES Employee(id)
);

CREATE TABLE Job(
    id INTEGER PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE Hired(
    id INTEGER PRIMARY KEY,
    employee INTEGER,
    job INTEGER,
    FOREIGN KEY (employee) REFERENCES Employee(id),
    FOREIGN KEY (job) REFERENCES Job(id)
);
