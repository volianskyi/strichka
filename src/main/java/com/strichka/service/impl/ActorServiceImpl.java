package com.strichka.service.impl;

import com.strichka.entity.Actor;
import com.strichka.repository.ActorRepository;
import com.strichka.service.ActorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public void save(Actor actor) {
        actorRepository.save(actor);
    }

    @Override
    public Actor findById(long id) {
        return actorRepository.findById(id);
    }

    @Override
    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    @Override
    public void remove(Actor actor) {
        actorRepository.remove(actor);
    }

    @Override
    public Actor getActorWithFetchMovies(long id) {
        return actorRepository.getActorWithFetchMovies(id);
    }


}
