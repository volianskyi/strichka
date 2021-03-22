package com.strichka.controller;

import com.strichka.entity.*;
import com.strichka.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        return "movie-form";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(@Validated @ModelAttribute("movie") Movie movie, BindingResult result) {
        if (result.hasErrors()) {
            return "movie-form";
        }
        movieService.save(movie);
        return "redirect:/";
    }

    @GetMapping("/movie/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@PathVariable long id, Model model) {
        Movie movie = movieService.findById(id);

        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@Validated @ModelAttribute("movie") Movie movie, BindingResult result) {
        if (result.hasErrors()) {
            return "update-movie";
        }
        movieService.save(movie);
        return "redirect:/movie/" + movie.getId();
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable long id) {
        movieService.remove(movieService.findById(id));
        return "redirect:/";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String getAll(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("movies", movies);
        return "list-movie";
    }

    @GetMapping("/movie/{id}/add-actor/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addActor(@PathVariable("id") long movie_id, @RequestParam("actor_id") long actor_id) {
        movieService.addActorToMovie(movie_id, actor_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/remove-actor/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String removeActor(@PathVariable("id") long movie_id, @RequestParam("actor_id") long actor_id) {
        movieService.removeActor(movie_id, actor_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/add-genre/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addGenre(@PathVariable("id") long movie_id, @RequestParam("genre_id") long genre_id) {
        movieService.addGenre(movie_id, genre_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/remove-genre/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String removeGenre(@PathVariable("id") long movie_id, @RequestParam("genre_id") long genre_id) {
        movieService.removeGenre(movie_id, genre_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/set-director/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String setDirector(@PathVariable("id") long movie_id, @RequestParam("director_id") long director_id) {
        directorService.addMovie(director_id, movie_id);
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/remove-director/{director_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
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
