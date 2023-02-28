package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskRepository {

    private static final String DELETE = "DELETE Task WHERE id = :fId";
    private static final String FIND_ALL = "FROM Task f JOIN FETCH f.priority ORDER BY f.id";
    private static final String FIND_CONDITION = "FROM Task f JOIN FETCH f.priority WHERE f.done = :isDone";
    private static final String FIND_BY_ID = "FROM Task f JOIN FETCH f.priority WHERE f.id = :tId";
    private static final String EXECUTE = "UPDATE Task SET done = :tDone WHERE id = :tId";
    private static final Logger LOG = LogManager.getLogger(TaskRepository.class);

    private final CrudRepository crudRepository;

    public Optional<Task> addTask(Task task) {
        try {
            crudRepository.run(session -> session.persist(task));
            return Optional.of(task);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Task> upgradeTask(Task task) {
        LOG.info("Задача: {} подготовлена к изменению", task);
        try {
            crudRepository.run(session -> session.merge(task));
            return Optional.of(task);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean deleteTask(int id) {
        return crudRepository.query(DELETE, Map.of("fId", id));
    }

    public List<Task> findAll() {
        return crudRepository.query(FIND_ALL, Task.class);
    }

    public List<Task> findConditionTasks(boolean check) {
        return crudRepository.query(FIND_CONDITION, Task.class, Map.of(
                "isDone", check
        ));
    }

    public Optional<Task> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Task.class, Map.of(
                "tId", id
        ));
    }

    public boolean executeTask(int id) {
        return crudRepository.query(EXECUTE, Map.of(
                "tDone", true,
                "tId", id
        ));
    }
}
