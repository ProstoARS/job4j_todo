CREATE TABLE IF NOT EXISTS category_task
(
    id SERIAL PRIMARY KEY,
    category_id INT NOT NULL REFERENCES categories(id),
    task_id INT NOT NULL REFERENCES tasks(id)
)