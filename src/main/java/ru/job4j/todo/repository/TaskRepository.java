package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class TaskRepository {

    private final SessionFactory sf;

    public Task addTask(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            log.info("Задача: {} добавлена в сессии: {}", task, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Задача {} не была добавлена", task, e);
        }
        return task;
    }

    public Task upgradeTask(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            log.info("Задача: {} обновлена в сессии: {}", task, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Задача {} не была обновлена", task, e);
        }
        return task;
    }

    public Boolean deleteTask(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            log.info("Задача: {} была удалёна в сессии: {}", task, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка удаления задачи {}", task, e);
            return false;
        }
        return true;
    }

    public List<Task> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Task order by id", Task.class).getResultList();
        }
    }

    public List<Task> findConditionTasks(Boolean check) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Task where done = :isDone", Task.class)
                    .setParameter("isDone", check)
                    .getResultList();
        }
    }
}
