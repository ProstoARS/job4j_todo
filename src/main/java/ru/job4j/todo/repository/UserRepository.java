package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {

    private static final Logger LOG = LogManager.getLogger(UserRepository.class);

    private final SessionFactory sf;

    public Optional<User> addUser(User user) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            LOG.info("Пользователь: {} был добавлен в сессии: {}", user, session);
            session.close();
            rsl = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка добавления пользователя: {} в сессии: {}", user, session);
        }
        return rsl;
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            rsl = session.createQuery("FROM User WHERE login = :tLogin and password = :tPassword", User.class)
                    .setParameter("tLogin", login)
                    .setParameter("tPassword", password)
                    .uniqueResultOptional();
            LOG.info("Пользователь: {} нашелся по логину: {} и паролю: {}", rsl.get(), login, password);
        } catch (Exception e) {
            LOG.error("Пользователь с логином: {} и паролем: {} не найден", login, password);
        }
        return rsl;
    }
}
