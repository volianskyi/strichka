package com.strichka.controller;

import com.strichka.entity.Director;
import com.strichka.entity.Movie;
import com.strichka.service.DirectorService;
import com.strichka.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/director")
public class DirectorController {

  private final DirectorService directorService;
  private final MovieService movieService;

  public DirectorController(DirectorService directorService, MovieService movieService) {
    this.directorService = directorService;
    this.movieService = movieService;
  }

  @GetMapping("/save")
  public String save(Model model) {
    Director director = new Director();
    model.addAttribute("director", director);
    return "director-form";
  }

  @PostMapping("/save")
  public String save(@Validated @ModelAttribute("director") Director director, BindingResult result) {
    if (result.hasErrors()) {
      return "director-form";
    }
    directorService.save(director);
    return "redirect:/director";
  }

  @GetMapping("/update/{id}")
  public String update(@PathVariable long id, Model model) {
    Director director = directorService.findById(id);
    model.addAttribute("director", director);
    return "update-director";
  }

  @PostMapping("/update")
  public String update(@Validated @ModelAttribute("director") Director director, BindingResult result) {
    if (result.hasErrors()) {
      return "update-director";
    }
    directorService.save(director);
    return "redirect:/director";
  }

  @GetMapping("/{id}")
  public String read(@PathVariable long id, Model model) {
    Director director = directorService.getDirectorFetchMovie(id);
    model.addAttribute("director", director);
    return "director-info";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable long id) {
    Director director = directorService.getDirectorFetchMovie(id);
    unsetDirectorFromAllMovies(id, director);
    directorService.remove(director);
    return "redirect:/director";
  }

  private void unsetDirectorFromAllMovies(long id, Director director) {
    for (Movie movie : director.getMovies()) {
      directorService.removeMovie(id, movie.getId());
    }
  }

  @GetMapping
  public String getAll(Model model) {
    List<Director> directorList = directorService.findAll();
    model.addAttribute("directors", directorList);
    return "list-director";
  }
}
