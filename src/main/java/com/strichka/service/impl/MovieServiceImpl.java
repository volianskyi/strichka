package com.strichka.service.impl;

import com.strichka.entity.Actor;
import com.strichka.entity.Director;
import com.strichka.entity.Genre;
import com.strichka.entity.Movie;
import com.strichka.repository.MovieRepository;
import com.strichka.service.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;


@Transactional
@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Movie findById(long id) {
        return movieRepository.findById(id);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public void remove(Movie movie) {
        movieRepository.remove(movie);
    }

    @Override
    public Movie findMovieByIdFetchActorsAndGenres(long id) {
        return movieRepository.findMovieByIdFetchActorsAndGenres(id);
    }

    @Override
    public void addActorToMovie(long movie_id, long actor_id) {
        movieRepository.addActorToMovie(movie_id, actor_id);
    }

    @Override
    public void removeActor(long movie_id, long actor_id) {
        movieRepository.removeActor(movie_id, actor_id);
    }

    @Override
    public void addGenre(long movie_id, long genre_id) {
        movieRepository.addGenre(movie_id, genre_id);
    }

    @Override
    public void removeGenre(long movie_id, long genre_id) {
        movieRepository.removeGenre(movie_id, genre_id);
    }


}
