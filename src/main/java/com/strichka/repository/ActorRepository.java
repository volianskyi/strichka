package com.strichka.repository;

import com.strichka.entity.Actor;

import java.util.List;

public interface ActorRepository {
    void save(Actor actor);

    Actor findById(long id);

    List<Actor> findAll();

    void remove(Actor actor);

    Actor getActorWithFetchMovies(long id);
}
