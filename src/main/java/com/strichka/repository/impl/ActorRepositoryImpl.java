package com.strichka.repository.impl;

import com.strichka.entity.Actor;
import com.strichka.repository.ActorRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class ActorRepositoryImpl implements ActorRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(Actor actor) {
        entityManager.merge(actor);
    }

    @Transactional(readOnly = true)
    @Override
    public Actor findById(long id) {
        return entityManager.find(Actor.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Actor> findAll() {
        return entityManager.createQuery("select a from Actor a", Actor.class).getResultList();
    }

    @Override
    public void remove(Actor actor) {
        entityManager.remove(entityManager.merge(actor));
    }

    @Transactional(readOnly = true)
    @Override
    public Actor getActorWithFetchMovies(long id) {
        return entityManager.createQuery("select a from Actor a left join fetch a.movies where a.id = :id", Actor.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
