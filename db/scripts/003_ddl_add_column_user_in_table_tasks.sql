ALTER table tasks
ADD COLUMN IF NOT EXISTS user_id int
REFERENCES users(id);
