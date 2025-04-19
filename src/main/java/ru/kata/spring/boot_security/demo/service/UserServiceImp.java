package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder getPasswordEncoder;

    public UserServiceImp(UserDao userDao, RoleService roleService, PasswordEncoder getPasswordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.getPasswordEncoder = getPasswordEncoder;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        Set<Role> roles = user.getRoles().stream().map(role -> roleService.findRolesById(role.getId()))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        user.setPassword(getPasswordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, int id) {
        User updatedUser = userDao.getUser(id);
        if (user.getRoles().isEmpty()) {
            user.setRoles(updatedUser.getRoles());
        }
        if (user.getPassword().isEmpty()) {
            user.setPassword(getPasswordEncoder.encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<User> user = userDao.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return user.get(0);
        }
    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(userDao.getUser(id));
    }

    private Set<Role> getRoles(User user) {
        Set<Role> listRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleService.findRoleByName(role.getName())
                    .stream()
                    .findFirst()
                    .orElse(null);
            listRoles.add(existingRole);
        }
        return listRoles;
    }
}
