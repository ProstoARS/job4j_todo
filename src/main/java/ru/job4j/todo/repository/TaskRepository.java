package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class TaskRepository {

    private static final Logger LOG = LogManager.getLogger(TaskRepository.class);

    private final SessionFactory sf;

    public Task addTask(Task task) {
        Session session = sf.openSession();
        Task newTask = Task.builder()
                .name(task.getName())
                .description(task.getDescription())
                .created(LocalDateTime.now())
                .done(false)
                .build();
        try {
            session.beginTransaction();
            session.save(newTask);
            session.getTransaction().commit();
            LOG.info("Задача: {} добавлена в сессии: {}", newTask, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Задача {} не была добавлена", task, e);
        }
        return newTask;
    }

    public Task upgradeTask(Task task) {
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

    public List<Task> findConditionTasks(Boolean check) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Task where done = :isDone", Task.class)
                    .setParameter("isDone", check)
                    .getResultList();
        }
    }
}
