CREATE TABLE IF NOT EXISTS priorities
(
    id       SERIAL PRIMARY KEY,
    name     TEXT UNIQUE NOT NULL,
    position int
);

comment on table priorities is 'Приоритет';
comment on column priorities.id is 'Идентификатор приоритета';
comment on column priorities.name is 'Значение приоритета';
comment on column priorities.position is 'Позиция приоритета';