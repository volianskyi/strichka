package com.strichka.service;

import com.strichka.entity.Director;
import com.strichka.entity.Movie;

import java.util.List;

public interface DirectorService {
    void save(Director director);

    Director findById(long id);

    List<Director> findAll();

    void remove(Director director);

    Director getDirectorFetchMovie(long id);

    void addMovie(long director_id, long movie_id);

    void removeMovie(long director_id, long movie_id);
}
