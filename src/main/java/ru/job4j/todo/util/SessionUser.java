package ru.job4j.todo.util;

import lombok.NoArgsConstructor;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;

@NoArgsConstructor
public class SessionUser {

    public static User getSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        } else {
            user.setName(user.getName());
        }
        return user;
    }
}
