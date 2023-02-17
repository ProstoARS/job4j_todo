CREATE TABLE IF NOT EXISTS categories
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR
);

comment on table categories is 'Категории';
comment on column categories.id is 'Идентификатор категории';
comment on column categories.name is 'Название категории';
