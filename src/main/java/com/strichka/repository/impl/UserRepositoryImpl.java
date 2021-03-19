package com.strichka.repository.impl;

import com.strichka.entity.User;
import com.strichka.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void save(User user) {
        entityManager.merge(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void remove(User user) {
        entityManager.remove(entityManager.merge(user));
    }

    @Override
    public User getUserByUsername(String username) {
        return entityManager.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
