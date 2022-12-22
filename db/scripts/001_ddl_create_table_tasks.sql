CREATE TABLE IF NOT EXISTS tasks (
                       id SERIAL PRIMARY KEY,
                       description TEXT,
                       created TIMESTAMP,
                       done BOOLEAN
);

comment on table tasks is 'Задачи';
comment on column tasks.id is 'Идентификатор задачи';
comment on column tasks.description is 'Описание задачи';
comment on column tasks.created is 'Время создания задачи';
comment on column tasks.done is 'Статус выполнения задачи';
