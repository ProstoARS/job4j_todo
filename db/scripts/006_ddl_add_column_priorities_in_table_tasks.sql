ALTER TABLE tasks
    ADD COLUMN priority_id int REFERENCES priorities (id);