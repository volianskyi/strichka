package com.strichka.repository;

import com.strichka.entity.Genre;

import java.util.List;

public interface GenreRepository {

    List<Genre> findAll();
}
