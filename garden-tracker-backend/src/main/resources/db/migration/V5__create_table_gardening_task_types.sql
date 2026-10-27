CREATE TABLE gardening_task_types(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR NOT NULL,
    category_id INT REFERENCES categories(id)
);