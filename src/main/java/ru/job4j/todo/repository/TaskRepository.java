package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskRepository {

    private static final Logger LOG = LogManager.getLogger(TaskRepository.class);

    private final SessionFactory sf;

    public Optional<Task> addTask(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            LOG.info("Задача: {} добавлена в сессии: {}", task, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Задача {} не была добавлена", task, e);
        }
        return Optional.of(task);
    }

    public Optional<Task> upgradeTask(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            LOG.info("Задача: {} обновлена в сессии: {}", task, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Задача {} не была обновлена", task, e);
        }
        return Optional.of(task);
    }

    public boolean deleteTask(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            LOG.info("Задача: {} была удалёна в сессии: {}", task, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка удаления задачи {}", task, e);
            return false;
        }
        return true;
    }

    public List<Task> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Task order by id", Task.class).getResultList();
        }
    }

    public List<Task> findConditionTasks(boolean check) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Task where done = :isDone", Task.class)
                    .setParameter("isDone", check)
                    .getResultList();
        }
    }

    public Optional<Task> findById(int id) {
        try (Session session = sf.openSession()) {
            return Optional.of(session.createQuery("from Task where id = :tId", Task.class)
                    .setParameter("tId", id)
                    .uniqueResult());
        }
    }

    public Optional<Task> executeTask(int id) {
        Optional<Task> taskFromDB = findById(id);
        if (taskFromDB.isPresent()) {
            Task task = taskFromDB.get();
            task.setDone(true);
            return upgradeTask(task);
        }
        return Optional.empty();
    }
}
