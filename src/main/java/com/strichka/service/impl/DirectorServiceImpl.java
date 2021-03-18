package com.strichka.service.impl;

import com.strichka.entity.Director;
import com.strichka.repository.DirectorRepository;
import com.strichka.service.DirectorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class DirectorServiceImpl implements DirectorService {

    private DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public void save(Director director) {
        directorRepository.save(director);
    }

    @Override
    public Director findById(long id) {
        return directorRepository.findById(id);
    }

    @Override
    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    @Override
    public void remove(Director director) {
        directorRepository.remove(director);
    }

    @Override
    public Director getDirectorFetchMovie(long id) {
        return directorRepository.getDirectorWithFetchMovies(id);
    }

    @Override
    public void addMovie(long director_id, long movie_id) {
        directorRepository.addMovie(director_id, movie_id);
    }

    @Override
    public void removeMovie(long director_id, long movie_id) {
        directorRepository.removeMovie(director_id, movie_id);
    }


}
