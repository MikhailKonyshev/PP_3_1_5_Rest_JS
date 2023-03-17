package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void updateUser(long id, User user);

    void deleteUser(long id);

    void saveUser(User user);

    List<User> getAllUsers();

    User getUserById(long id);

}
