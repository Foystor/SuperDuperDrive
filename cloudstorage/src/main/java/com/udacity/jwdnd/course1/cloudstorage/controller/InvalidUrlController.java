package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvalidUrlController implements ErrorController {

    @GetMapping("/error")
    public String showError() {
        return "errorPage";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
