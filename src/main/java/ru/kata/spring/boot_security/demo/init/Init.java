package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleService.saveRole(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleService.saveRole(userRole);

        User admin = new User();
        admin.setName("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@admin.com");
        admin.setRoles(Set.of(adminRole));
        userService.saveUser(admin);

        User user = new User();
        user.setName("user");
        user.setPassword("user");
        user.setEmail("user@user.com");
        user.setRoles(Set.of(userRole));
        userService.saveUser(user);
    }
}