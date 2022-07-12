package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public boolean isNoteTitleAvailable(String noteTitle) {
        return noteMapper.getNote(noteTitle) == null;
    }

    public int createNote(Note note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userMapper.getUser(authentication.getName());

        return noteMapper.insertNote(
                new Note(null,
                        note.getNoteTitle(),
                        note.getNoteDescription(),
                        user.getUserId())
        );
    }

    public List<Note> getNoteList() {
        return noteMapper.getNoteList();
    }

    public Note getNote(String noteTitle) {
        return noteMapper.getNote(noteTitle);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public void deleteNote(String noteTitle) {
        noteMapper.deleteNote(noteTitle);
    }
}
