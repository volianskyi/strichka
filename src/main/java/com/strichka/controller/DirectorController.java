package com.strichka.controller;

import com.strichka.entity.Director;
import com.strichka.entity.Movie;
import com.strichka.loging.LoggingAspect;
import com.strichka.service.DirectorService;
import com.strichka.service.MovieService;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/director")
public class DirectorController {

  private static final Logger logger = Logger.getLogger(DirectorController.class);

  private final DirectorService directorService;
  private final MovieService movieService;

  public DirectorController(DirectorService directorService, MovieService movieService) {
    this.directorService = directorService;
    this.movieService = movieService;
  }

  @GetMapping("/save")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String save(Model model) {
    Director director = new Director();
    model.addAttribute("director", director);
    logger.info("Open director save page...");
    return "director-form";
  }

  @PostMapping("/save")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String save(@Validated @ModelAttribute("director") Director director, BindingResult result) {
    if (result.hasErrors()) {
      return "director-form";
    }
    directorService.save(director);
    logger.info("Created director " + director);
    return "redirect:/director";
  }

  @GetMapping("/update/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String update(@PathVariable long id, Model model) {
    Director director = directorService.findById(id);
    model.addAttribute("director", director);
    logger.info("Open director update page...");
    return "update-director";
  }

  @PostMapping("/update")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String update(@Validated @ModelAttribute("director") Director director, BindingResult result) {
    if (result.hasErrors()) {
      return "update-director";
    }
    directorService.save(director);
    logger.info("Updated director " + director);
    return "redirect:/director";
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public String read(@PathVariable long id, Model model) {
    Director director = directorService.getDirectorFetchMovie(id);
    model.addAttribute("director", director);
    logger.info("Open director info page");
    return "director-info";
  }

  @GetMapping("/delete/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String delete(@PathVariable long id) {
    Director director = directorService.getDirectorFetchMovie(id);
    unsetDirectorFromAllMovies(id, director);
    directorService.remove(director);
    logger.info("Deleted director with id: " + id);
    return "redirect:/director";
  }

  private void unsetDirectorFromAllMovies(long id, Director director) {
    for (Movie movie : director.getMovies()) {
      directorService.removeMovie(id, movie.getId());
    }
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public String getAll(Model model) {
    List<Director> directorList = directorService.findAll();
    model.addAttribute("directors", directorList);
    logger.info("Return all directors");
    return "list-director";
  }
}
