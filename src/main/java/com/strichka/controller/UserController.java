package com.strichka.controller;

import com.strichka.entity.User;
import com.strichka.loging.LoggingAspect;
import com.strichka.service.RoleService;
import com.strichka.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/create")
    public String create(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        logger.info("Open user save page...");
        return "user-form";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user-form";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findById(2));
        userService.save(user);
        logger.info("Created user " + user);
        return "redirect:/";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.id == #id")
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
        logger.info("Open user update page...");
        return "update-user";
    }

    @PostMapping("/update")
    public String update(@Validated @ModelAttribute("user") User user,
                         BindingResult result, Model model,
                         @RequestParam("oldPassword") String oldPassword,
                         @RequestParam(value = "roleId", required = false, defaultValue = "2") long roleId) {
        User oldUser = userService.findById(user.getId());
        if (result.hasErrors()) {
            user.setRole(oldUser.getRole());
            model.addAttribute("roles", roleService.findAll());
            return "update-user";
        }
        if (!passwordEncoder.matches(oldPassword, oldUser.getPassword())) {
            result.addError(new FieldError("user", "password", "Invalid old password"));
            user.setRole(oldUser.getRole());
            model.addAttribute("roles", roleService.findAll());
            return "update-user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findById(roleId));
        userService.save(user);
        logger.info("Updated user " + user);
        return "redirect:/user/";
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.id == #id")
    public String delete(@PathVariable long id) {
        userService.remove(userService.findById(id));
        logger.info("Deleted user with id: " + id);
        return "redirect:/user";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String getAll(Model model) {
         List<User> users = userService.findAll();
         model.addAttribute("users", users);
        logger.info("Return all users");
         return "list-user";
    }
}
