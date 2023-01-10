package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

@Repository
@AllArgsConstructor
public class UserRepository {

    private static final Logger LOG = LogManager.getLogger(UserRepository.class);

    private final SessionFactory sf;

    public User addUser(User user) {
        Session session = sf.openSession();
        User newUser = User.builder()
                .name(user.getName())
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
        try {
            session.beginTransaction();
            session.save(newUser);
            session.getTransaction().commit();
            LOG.info("Пользователь: {} был добавлен в сессии: {}", newUser, session);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка добавления пользователя: {} ", newUser);
        }
        return newUser;
    }

    public User findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        return session.createQuery("FROM User WHERE login = :tLogin and password = :tPassword", User.class)
                .setParameter("tLogin", login)
                .setParameter("tPassword", password)
                .uniqueResult();
    }

}
