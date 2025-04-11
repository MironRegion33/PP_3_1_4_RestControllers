package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    public List<Role> getAllRoles();

    public Set<Role> findRoleByName(String name);

    public void saveRole(Role role);
}
