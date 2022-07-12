package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

        note.setNoteTitle(note.getNoteTitle().trim());

        if (note.getNoteId() == null) {
            // create note
            // check if note title duplicate
            if (!noteService.isNoteTitleAvailable(note.getNoteTitle())) {
                uploadErrorMsg = "The note title already exists.";
            }

            if (uploadErrorMsg == null) {
                int rowsAdded = noteService.createNote(note);
                if (rowsAdded < 0) saveErrorMsg = true;
            }
        } else {
            // update note
            // check if note title duplicate
            if (noteService.isNoteTitleAvailable(note.getNoteTitle())) {
                noteService.updateNote(note);
            } else {
                Note checkNote = noteService.getNote(note.getNoteTitle());
                if (Objects.equals(checkNote.getNoteId(), note.getNoteId())) {
                    // check if there is changes
                    if (checkNote.getNoteDescription().equals(note.getNoteDescription())) {
                        uploadErrorMsg = "No changes has been made.";
                    } else {
                        noteService.updateNote(note);
                    }
                } else {
                    uploadErrorMsg = "The note title already exists.";
                }
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

    @GetMapping("/delete/{noteTitle}")
    public String deleteNote(@PathVariable("noteTitle") String noteTitle) {
        noteService.deleteNote(noteTitle);
        return "redirect:/home";
    }
}
