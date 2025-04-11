package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImp implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("select r from Role r", Role.class)
                .getResultList();
    }

    @Override
    public Set<Role> findRoleByName(String name) {
        List<Role> roles = entityManager.createQuery(
                        "select r from Role r where r.name = :name", Role.class)
                .setParameter("name", name)
                .getResultList();
        return new HashSet<>(roles);
    }

    @Override
    public void saveRole(Role role) {
        if (role.getId() == 0) {
            entityManager.persist(role);
        } else {
            entityManager.merge(role);
        }
    }
}
