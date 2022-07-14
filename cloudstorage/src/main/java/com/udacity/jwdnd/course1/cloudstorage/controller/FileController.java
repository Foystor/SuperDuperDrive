package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home/file")
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

    @GetMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable("fileName") String fileName, Model model) {
        fileService.deleteFile(fileName);
        model.addAttribute("success",true);
        return "result";
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<byte[]> viewFile(@PathVariable("fileName") String fileName) {
        File file = fileService.getFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"")
                .body(file.getFileData());
    }
}
