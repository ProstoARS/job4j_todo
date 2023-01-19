package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {

    private static final Logger LOG = LogManager.getLogger(UserRepository.class);
    private static final String FIND_BY_LOGIN_AND_PASSWORD = """
            FROM User
            WHERE login = :tLogin
            AND password = :tPassword
            """;

    private final CrudRepository crudRepository;

    public Optional<User> addUser(User user) {
        crudRepository.run(session -> session.persist(user));
        return Optional.of(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(FIND_BY_LOGIN_AND_PASSWORD, User.class, Map.of(
                "tLogin", login,
                "tPassword", password
        ));
    }
}
