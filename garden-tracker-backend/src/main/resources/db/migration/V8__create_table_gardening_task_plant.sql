CREATE TABLE gardening_task_plant(
    gardening_task_id UUID REFERENCES gardening_tasks(id),
    plant_id INT REFERENCES plants(id),
    PRIMARY KEY(gardening_task_id, plant_id)
);