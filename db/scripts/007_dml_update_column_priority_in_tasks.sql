UPDATE tasks
SET priority_id = (SELECT id FROM priorities WHERE name = 'urgently');