package com.strichka.controller;

import com.strichka.entity.Actor;
import com.strichka.entity.Director;
import com.strichka.entity.Genre;
import com.strichka.entity.Movie;
import com.strichka.service.ActorService;
import com.strichka.service.DirectorService;
import com.strichka.service.GenreService;
import com.strichka.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MovieController {

    private final MovieService movieService;
    private final ActorService actorService;
    private final GenreService genreService;
    private final DirectorService directorService;

    @Autowired
    public MovieController(MovieService movieService, ActorService actorService, GenreService genreService, DirectorService directorService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.genreService = genreService;
        this.directorService = directorService;
    }

    @GetMapping("login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/save")
    public String create(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        return "movie-form";
    }

    @PostMapping("/save")
    public String create(@Validated @ModelAttribute("movie") Movie movie, BindingResult result) {
        if (result.hasErrors()) {
            return "movie-form";
        }
        movieService.save(movie);
        return "redirect:/";
    }

    @GetMapping("/movie/{id}")
    public String read(@PathVariable long id, Model model) {
        Movie movie = movieService.findMovieByIdFetchActorsAndGenres(id);
        List<Actor> actorList = getNotAddedMovies(movie);
        List<Genre> genreList = getNotAddedGenres(movie);
        List<Director> directorList = getDirectors(movie);


        model.addAttribute("directors", directorList);
        model.addAttribute("actors", actorList);
        model.addAttribute("genres", genreList);
        model.addAttribute("movie", movie);
        return "movie-info";
    }


    @GetMapping("/update/{id}")
    public String update(@PathVariable long id, Model model) {
        Movie movie = movieService.findById(id);

        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @PostMapping("/update")
    public String update(@Validated @ModelAttribute("movie") Movie movie, BindingResult result) {
        if (result.hasErrors()) {
            return "update-movie";
        }
        movieService.save(movie);
        return "redirect:/movie/" + movie.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        movieService.remove(movieService.findById(id));
        return "redirect:/";
    }

    @GetMapping
    public String getAll(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("movies", movies);
        return "list-movie";
    }

    @GetMapping("/movie/{id}/add-actor/")
    public String addActor(@PathVariable("id") long movie_id, @RequestParam("actor_id") long actor_id) {
        movieService.addActorToMovie(movie_id, actor_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/remove-actor/")
    public String removeActor(@PathVariable("id") long movie_id, @RequestParam("actor_id") long actor_id) {
        movieService.removeActor(movie_id, actor_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/add-genre/")
    public String addGenre(@PathVariable("id") long movie_id, @RequestParam("genre_id") long genre_id) {
        movieService.addGenre(movie_id, genre_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/remove-genre/")
    public String removeGenre(@PathVariable("id") long movie_id, @RequestParam("genre_id") long genre_id) {
        movieService.removeGenre(movie_id, genre_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/set-director/")
    public String setDirector(@PathVariable("id") long movie_id, @RequestParam("director_id") long director_id) {
        directorService.addMovie(director_id, movie_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/remove-director/{director_id}")
    public String removeDirector(@PathVariable("id") long movie_id, @PathVariable("director_id") long director_id) {
        directorService.removeMovie(director_id, movie_id);
        return "redirect:/movie/" + movie_id;
    }

    private List<Actor> getNotAddedMovies(Movie movie) {
        return actorService.findAll().stream()
                .filter(actor -> !(movie.getActors().contains(actor)))
                .collect(Collectors.toList());
    }

    private List<Genre> getNotAddedGenres(Movie movie) {
        return genreService.findAll().stream()
                .filter(genre -> !(movie.getGenres().contains(genre)))
                .collect(Collectors.toList());
    }

    private List<Director> getDirectors(Movie movie) {
        List<Director> directorList;
        if (movie.getDirector() != null) {
            directorList = directorService.findAll().stream()
                    .filter(director -> !(movie.getDirector().equals(director)))
                    .collect(Collectors.toList());
        } else {
            directorList = directorService.findAll();
        }
        return directorList;
    }
}
