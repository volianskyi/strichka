package com.strichka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView entityNotFoundExceptionHandler(HttpServletRequest request, NoResultException exception) {
        return getModelAndView(request, HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ModelAndView accessForbiddenExceptionHandler(HttpServletRequest request) {
        return getModelAndView(request, HttpStatus.FORBIDDEN, "Access Denied");
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView internalServerErrorHandler(HttpServletRequest request, Exception exception) {
        return getModelAndView(request, HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ModelAndView getModelAndView(HttpServletRequest request, HttpStatus httpStatus, Exception exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("code", httpStatus.value() + " / " + httpStatus.getReasonPhrase());
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

    private ModelAndView getModelAndView(HttpServletRequest request, HttpStatus httpStatus, String message) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("code", httpStatus.value() + " / " + httpStatus.getReasonPhrase());
        modelAndView.addObject("message", message);
        return modelAndView;
    }

}
