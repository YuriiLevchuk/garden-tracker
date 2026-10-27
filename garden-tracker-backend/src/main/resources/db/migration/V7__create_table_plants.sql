CREATE TABLE plants(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    garden_id INT REFERENCES gardens(id),
    plant_type INT REFERENCES plant_types(id)
);