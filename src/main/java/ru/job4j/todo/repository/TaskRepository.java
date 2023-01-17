package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        Optional<Task> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            LOG.info("Задача: {} добавлена в сессии: {}", task, session);
            session.close();
            rsl = Optional.of(task);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Задача {} не была добавлена", task, e);
        }
        return rsl;
    }

    public Optional<Task> upgradeTask(Task task) {
        Session session = sf.openSession();
        Optional<Task> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            LOG.info("Задача: {} обновлена в сессии: {}", task, session);
            session.close();
            rsl = Optional.of(task);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Задача {} не была обновлена", task, e);
        }
        return rsl;
    }

    public boolean deleteTask(int id) {
        Session session = sf.openSession();
        int executeUpdate = 0;
        try {
            session.beginTransaction();
            executeUpdate = session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            LOG.info("Задача id={} была удалёна в сессии: {}", id, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка удаления задачи id={}", id, e);
        }
        return executeUpdate > 0;
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
            return session.createQuery("from Task where id = :tId", Task.class)
                    .setParameter("tId", id)
                    .uniqueResultOptional();
        }
    }

    public boolean executeTask(int id) {
        Session session = sf.openSession();
        int executeUpdate = 0;
        try {
            Transaction transaction = session.beginTransaction();
             executeUpdate = session.createQuery("update Task set done = :tDone where id = :tId")
                    .setParameter("tDone", true)
                    .setParameter("tId", id)
                    .executeUpdate();
            transaction.commit();
            LOG.info("Задача под id={} успешно обновлена как выполненная в сессии {}", id, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Обновление задачи под id={} как выполненная не произошло", id, e);
        }
        return executeUpdate > 0;
    }
}
