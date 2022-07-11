package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String homeView(Model model) {
        model.addAttribute("fileList", fileService.getFiles());
        return "home";
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
        String uploadErrorMsg = null;
        boolean saveErrorMsg = false;

        // check if file is empty
        if (file.isEmpty()) {
            uploadErrorMsg = "Please select a file to upload.";
        }

        // check if file name duplicate
        if (uploadErrorMsg == null) {
            if (!fileService.isFileNameAvailable(file.getOriginalFilename())) {
                uploadErrorMsg = "The file name already exists.";
            }
        }

        if (uploadErrorMsg == null) {
            int rowsAdded = fileService.createFile(file);
            if (rowsAdded < 0) {
                saveErrorMsg = true;
            }
        }

        if (uploadErrorMsg == null) {
            if (saveErrorMsg) {
                model.addAttribute("saveErrorMsg",true);
            } else {
                model.addAttribute("success",true);
            }
        } else {
            model.addAttribute("uploadErrorMsg", uploadErrorMsg);
        }

        return "result";
    }
}
