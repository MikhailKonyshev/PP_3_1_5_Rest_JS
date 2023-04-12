package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntFunction;


@Service
@Transactional
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(long id, User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User user1 = userRepository.getById(id);
//        user1.setId(user.getId());
//        user1.setUsername(user.getUsername());
//        user1.setLastName(user.getLastName());
//        user1.setAge(user.getAge());
//        user1.setEmail(user.getEmail());
//        user1.setPassword(user.getPassword());
//        user1.setRoles(user.getRoles());

        Optional<User> editedUser = userRepository.findById(user.getId());
        if (user.getPassword().isEmpty() &  editedUser.isPresent()) {
            user.setPassword(editedUser.get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(new User());
    }
}
