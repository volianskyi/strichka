package com.strichka.controller;

import com.strichka.entity.*;
import com.strichka.loging.LoggingAspect;
import com.strichka.service.*;
import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(MovieController.class);

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
        logger.info("Logging page");
        return "login";
    }

    @GetMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        logger.info("Open movie save page...");
        return "movie-form";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(@Validated @ModelAttribute("movie") Movie movie, BindingResult result) {
        if (result.hasErrors()) {
            return "movie-form";
        }
        movieService.save(movie);
        logger.info("Created movie " + movie);
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

        logger.info("Open movie info page");
        return "movie-info";
    }


    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@PathVariable long id, Model model) {
        Movie movie = movieService.findById(id);

        model.addAttribute("movie", movie);
        logger.info("Open movie update page...");
        return "update-movie";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@Validated @ModelAttribute("movie") Movie movie, BindingResult result) {
        if (result.hasErrors()) {
            return "update-movie";
        }
        Movie oldMovie = movieService.findMovieByIdFetchActorsAndGenres(movie.getId());
        movie.setDirector(oldMovie.getDirector());
        movie.setActors(oldMovie.getActors());
        movie.setGenres(oldMovie.getGenres());
        movieService.save(movie);
        logger.info("Updated movie " + movie);
        return "redirect:/movie/" + movie.getId();
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable long id) {
        movieService.remove(movieService.findById(id));
        logger.info("Deleted movie with id: " + id);
        return "redirect:/";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String getAll(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("movies", movies);
        logger.info("Return all movies");
        return "list-movie";
    }

    @GetMapping("/country/{country}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String getAllByCountry(@PathVariable String country, Model model) {
        List<Movie> movies = movieService.findAllByCountry(country);
        model.addAttribute("movies", movies);
        logger.info("Return all movies by country");
        return "list-movie";
    }

    @GetMapping("/movie/{id}/add-actor/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addActor(@PathVariable("id") long movie_id, @RequestParam("actor_id") long actor_id) {
        movieService.addActorToMovie(movie_id, actor_id);
        logger.info("Add actor to movie");
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/remove-actor/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String removeActor(@PathVariable("id") long movie_id, @RequestParam("actor_id") long actor_id) {
        movieService.removeActor(movie_id, actor_id);
        logger.info("Remove actor from movie");
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/add-genre/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addGenre(@PathVariable("id") long movie_id, @RequestParam("genre_id") long genre_id) {
        movieService.addGenre(movie_id, genre_id);
        logger.info("Add genre to movie");
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/remove-genre/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String removeGenre(@PathVariable("id") long movie_id, @RequestParam("genre_id") long genre_id) {
        movieService.removeGenre(movie_id, genre_id);
        logger.info("Remove genre from movie");
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/set-director/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String setDirector(@PathVariable("id") long movie_id, @RequestParam("director_id") long director_id) {
        directorService.addMovie(director_id, movie_id);
        logger.info("Set director");
        return "redirect:/movie/" + movie_id;
    }

    @GetMapping("/movie/{id}/remove-director/{director_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String removeDirector(@PathVariable("id") long movie_id, @PathVariable("director_id") long director_id) {
        directorService.removeMovie(director_id, movie_id);
        logger.info("Unset director");
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
