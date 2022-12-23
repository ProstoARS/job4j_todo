package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

@Repository
@AllArgsConstructor
public class TaskRepository {

    public static final Logger log = LoggerFactory.getLogger(TaskRepository.class.getName());

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
