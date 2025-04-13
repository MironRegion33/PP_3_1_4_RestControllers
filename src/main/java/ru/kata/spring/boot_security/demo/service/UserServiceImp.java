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
    public void saveUser(User user, String[] roles) {
        user.setPassword(getPasswordEncoder.encode(user.getPassword()));
        Set<Role> listRoles = new HashSet<>();
        if (roles != null) {
            for (String roleName : roles) {
                Set<Role> foundRoles = roleService.findRoleByName(roleName);
                listRoles.addAll(foundRoles);
            }
        } else {
            listRoles.addAll(roleService.findRoleByName("ROLE_USER"));
        }
        user.setRoles(listRoles);
        if (user.getId() == 0) {
            System.err.println("принял " + user + " save");
            userDao.saveUser(user);
        } else {
            System.err.println("принял " + user + " update");
            userDao.updateUser(user);
        }
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, String[] roles) {
        Set<Role> listRoles = new HashSet<>();
        if (roles != null) {
            for (String roleName : roles) {
                Set<Role> foundRoles = roleService.findRoleByName(roleName);
                listRoles.addAll(foundRoles);
            }
        }
        user.setRoles(listRoles);
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
}
