package com.strichka.repository.impl;

import com.strichka.entity.Actor;
import com.strichka.entity.Director;
import com.strichka.entity.Genre;
import com.strichka.entity.Movie;
import com.strichka.repository.MovieRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class MovieRepositoryImpl implements MovieRepository {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public void save(Movie movie) {
    entityManager.merge(movie);
  }

  @Transactional(readOnly = true)
  @Override
  public Movie findById(long id) {
    return entityManager.find(Movie.class, id);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Movie> findAll() {
    return entityManager.createQuery("select m from Movie m", Movie.class)
            .getResultList();
  }

  @Override
  public void remove(Movie movie) {
    entityManager.remove(entityManager.merge(movie));
  }

  @Transactional(readOnly = true)
  @Override
  public Movie findMovieByIdFetchActorsAndGenres(long id) {
      return entityManager.createQuery("select m from Movie m left join fetch m.actors left join fetch m.genres where m.id = :id", Movie.class)
              .setParameter("id", id)
              .getSingleResult();
  }

  @Override
  public void addActorToMovie(long movie_id, long actor_id) {
    Movie movie = entityManager.getReference(Movie.class, movie_id);
    Actor actor = entityManager.getReference(Actor.class, actor_id);
    movie.addActor(actor);
  }

  @Override
  public void removeActor(long movie_id, long actor_id) {
    Movie movie = entityManager.getReference(Movie.class, movie_id);
    Actor actor = entityManager.getReference(Actor.class, actor_id);
    movie.removeActor(actor);
  }

  @Override
  public void addGenre(long movie_id, long genre_id) {
    Movie movie = entityManager.getReference(Movie.class, movie_id);
    Genre genre = entityManager.getReference(Genre.class, genre_id);
    movie.addGenre(genre);
  }

  @Override
  public void removeGenre(long movie_id, long genre_id) {
    Movie movie = entityManager.getReference(Movie.class, movie_id);
    Genre genre = entityManager.getReference(Genre.class, genre_id);
    movie.removeGenre(genre);
  }

  @Override
  public List<Movie> findAllByCountry(String country) {
    return entityManager.createQuery("select m from Movie m where m.country = :country", Movie.class)
            .setParameter("country", country)
            .getResultList();
  }

}
