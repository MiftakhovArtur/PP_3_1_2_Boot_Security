package ru.kata.spring.boot_security.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role findByName(String name) {
        return entityManager.createQuery(
                        "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public void delete(Role role) {
        entityManager.remove(entityManager.contains(role) ? role : entityManager.merge(role));
    }
}
