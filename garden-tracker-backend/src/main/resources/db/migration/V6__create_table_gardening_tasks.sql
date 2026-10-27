CREATE TABLE gardening_tasks(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    realization_time TIMESTAMPTZ NOT NULL,
    user_id INT REFERENCES users(id),
    garden_id INT REFERENCES gardens(id),
    gardening_work_type INT REFERENCES gardening_task_types(id)
);