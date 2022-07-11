package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/upload")
    public String uploadNote(@ModelAttribute("newNote") Note note, Model model) {
        String uploadErrorMsg = null;
        boolean saveErrorMsg = false;

        // check if note name duplicate
        if (!noteService.isNoteTitleAvailable(note.getNoteTitle().trim())) {
            uploadErrorMsg = "The note name already exists.";
        }

        if (uploadErrorMsg == null) {
            int rowsAdded = noteService.createNote(note);
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
