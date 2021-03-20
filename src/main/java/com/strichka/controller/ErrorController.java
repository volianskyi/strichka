package com.strichka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ErrorController {

    @RequestMapping(value = "not-found", method = RequestMethod.GET)
    public ModelAndView renderErrorPage() {
        ModelAndView notFound = new ModelAndView("404");
        String message = "404 / Not Found";
        notFound.addObject("message", message);
        return notFound;
    }

    @RequestMapping(value = "access-denied", method = RequestMethod.GET)
    public ModelAndView renderAccessErrorPage() {
        ModelAndView accessDenied = new ModelAndView("403");
        String message = "403 / Access Denied";
        accessDenied.addObject("message", message);
        return accessDenied;
    }

}
