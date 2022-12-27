package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

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
            log.info("Задача добавлена {}", task);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Объект {} не был добавлен", task, e);
        }
        return task;
    }
}
