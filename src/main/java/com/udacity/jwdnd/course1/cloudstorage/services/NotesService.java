package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    final UserService userService;
    final NotesMapper notesMapper;

    public NotesService(UserService userService, NotesMapper notesMapper) {
        this.userService = userService;
        this.notesMapper = notesMapper;
    }


    public List<Note> getNotes() {
        return notesMapper.getNotes(userService.getCurrentUserId());
    }

    public void createNote(NoteForm noteForm) {
        Note note = new Note(noteForm.getNoteTitle(), noteForm.getNoteDescription(), userService.getCurrentUserId());
        notesMapper.insertNote(note);
    }

    public Note getNoteById(int id) {
        return notesMapper.getNoteById(id, userService.getCurrentUserId());
    }

    public void updateNotes(int noteId, NoteForm noteForm) {
        Note note = new Note(noteId, noteForm.getNoteTitle(), noteForm.getNoteDescription());
        notesMapper.updateNote(note);
    }

    public void deleteNote(int noteId) {
        notesMapper.deleteNote(noteId, userService.getCurrentUserId());
    }

}
