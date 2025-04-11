package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    void saveUser(User user, String[] roles);

    User getUser(int id);

    void updateUser(User user, String[] roles);

    void deleteUser(int id);

    Optional<User> getUserById(int id);
}
