package org.ait.project.guideline.example.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author febrihasan
 */
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Custom exception handling logic goes here
        // Can inspect the exception, request, and response to determine how to handle the exception

        // For simplicity, let's just return a ModelAndView with an error view
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", "An error occurred: " + ex.getMessage());

        return modelAndView;
    }
}
