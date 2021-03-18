package com.strichka.service;

import com.strichka.entity.Actor;

import java.util.List;

public interface ActorService {
    void save(Actor actor);

    Actor findById(long id);

    List<Actor> findAll();

    void remove(Actor actor);

    Actor getActorWithFetchMovies(long id);
}
