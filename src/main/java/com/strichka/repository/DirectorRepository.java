package com.strichka.repository;

import com.strichka.entity.Director;

import java.util.List;

public interface DirectorRepository {
    void save(Director director);

    Director findById(long id);

    List<Director> findAll();

    void remove(Director director);

    Director getDirectorWithFetchMovies(long id);

    void addMovie(long director_id, long movie_id);

    void removeMovie(long director_id, long movie_id);

}
