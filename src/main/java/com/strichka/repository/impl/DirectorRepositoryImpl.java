package com.strichka.repository.impl;

import com.strichka.entity.Director;
import com.strichka.entity.Movie;
import com.strichka.repository.DirectorRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class DirectorRepositoryImpl implements DirectorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Director director) {
        entityManager.merge(director);
    }

    @Transactional(readOnly = true)
    @Override
    public Director findById(long id) {
        return entityManager.find(Director.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Director> findAll() {
        return entityManager.createQuery("select d from Director d", Director.class).getResultList();
    }

    @Override
    public void remove(Director director) {
        entityManager.remove(entityManager.merge(director));
    }

    @Transactional(readOnly = true)
    @Override
    public Director getDirectorWithFetchMovies(long id) {
        return entityManager.createQuery("select d from Director d left join fetch d.movies where d.id = :id", Director.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void addMovie(long director_id, long movie_id) {
        Director tempDirector = entityManager.find(Director.class, director_id);
        Movie tempMovie = entityManager.find(Movie.class, movie_id);
        tempDirector.addMovie(tempMovie);
    }

    @Override
    public void removeMovie(long director_id, long movie_id) {
        Director tempDirector = entityManager.find(Director.class, director_id);
        Movie tempMovie = entityManager.find(Movie.class, movie_id);
        tempDirector.removeMovie(tempMovie);
    }

}
