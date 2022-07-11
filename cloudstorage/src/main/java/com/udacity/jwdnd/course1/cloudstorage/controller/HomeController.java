package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String homeView() {
        return "home";
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {

        // check if file is empty
        if (file.isEmpty()) {
            model.addAttribute("uploadErrorMsg","Please select a file to upload.");
        } else {
            model.addAttribute("success",true);
        }
        return "result";
    }
}
