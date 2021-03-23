package com.strichka.controller;

import com.strichka.entity.Actor;
import com.strichka.loging.LoggingAspect;
import com.strichka.service.ActorService;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/actor")
public class ActorController {

    private static final Logger logger = Logger.getLogger(ActorController.class);

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(Model model) {
        Actor actor = new Actor();
        model.addAttribute("actor", actor);
        logger.info("Open actor save page...");
        return "actor-form";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(@Validated @ModelAttribute("actor") Actor actor, BindingResult result) {
        if (result.hasErrors()) {
            return "actor-form";
        }
        actorService.save(actor);
        logger.info("Created actor " + actor);
        return "redirect:/actor";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@PathVariable long id, Model model) {
        Actor actor = actorService.findById(id);
        model.addAttribute("actor", actor);
        logger.info("Open actor update page...");
        return "update-actor";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@Validated @ModelAttribute("actor") Actor actor, BindingResult result) {
        if (result.hasErrors()) {
            return "update-actor";
        }
        actorService.save(actor);
        logger.info("Updated actor " + actor);
        return "redirect:/actor/" + actor.getId();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String read(@PathVariable long id, Model model) {
        model.addAttribute("actor", actorService.getActorWithFetchMovies(id));
        logger.info("Open actor info page");
        return "actor-info";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable long id) {
        actorService.remove(actorService.findById(id));
        logger.info("Deleted actor with id: " + id);
        return "redirect:/actor";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String getAll(Model model) {
        List<Actor> actors = actorService.findAll();
        model.addAttribute("actors", actors);
        logger.info("Return all actors");
        return "list-actor";
    }
}
