ALTER table tasks
ADD COLUMN IF NOT EXISTS user_id int
REFERENCES users(id);

-- comment on column user_id is 'Связь с таблицей Users many-to-one';