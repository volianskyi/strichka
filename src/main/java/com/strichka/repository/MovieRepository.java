package com.strichka.repository;

import com.strichka.entity.Movie;

import java.util.List;

public interface MovieRepository {

  void save(Movie movie);

  Movie findById(long id);

  List<Movie> findAll();

  void remove(Movie movie);

  Movie findMovieByIdFetchActorsAndGenres(long id);

  void addActorToMovie(long movie_id, long actor_id);

  void removeActor(long movie_id, long actor_id);

  void addGenre(long movie_id, long genre_id);

  void removeGenre(long movie_id, long genre_id);

}
