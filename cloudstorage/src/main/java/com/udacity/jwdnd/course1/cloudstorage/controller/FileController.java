package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
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
