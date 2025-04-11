package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    public void saveUser(User user, String[] roles);

    public User getUser(int id);

    public void updateUser(User user, String[] roles);

    public void deleteUser(int id);

    public Optional<User> getUserById(int id);
}
