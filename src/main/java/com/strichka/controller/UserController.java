package com.strichka.controller;

import com.strichka.entity.User;
import com.strichka.service.RoleService;
import com.strichka.service.UserService;
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
        return "redirect:/";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.id == #id")
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
        return "update-user";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("oldPassword") String oldPassword,
                         @RequestParam("roleId") long roleId, BindingResult result, Model model) {
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
        return "redirect:/user/";
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.id == #id")
    public String delete(@PathVariable long id) {
        userService.remove(userService.findById(id));
        return "redirect:/user";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String getAll(Model model) {
         List<User> users = userService.findAll();
         model.addAttribute("users", users);
         return "list-user";
    }
}
