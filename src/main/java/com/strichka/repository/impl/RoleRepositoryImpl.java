package com.strichka.repository.impl;

import com.strichka.entity.Role;
import com.strichka.repository.RoleRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(Role role) {
        entityManager.merge(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Role findById(long id) {
        return entityManager.find(Role.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }
}
