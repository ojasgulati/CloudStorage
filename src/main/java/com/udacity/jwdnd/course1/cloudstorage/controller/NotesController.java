package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/home/notes")
public class NotesController {

    final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/{noteId}")
    public Note getNotes(@PathVariable("noteId") int noteId) {
        return notesService.getNoteById(noteId);
    }


    @PostMapping
    public String createNewNote(@ModelAttribute("noteForm") NoteForm note, Model model) {
        if (Objects.isNull(note.getNoteId()))
            notesService.createNote(note);
        else
            notesService.updateNotes(note.getNoteId(), note);
        return "result";
    }

    @PostMapping("/deletenote/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId) {
        notesService.deleteNote(noteId);
        return "result";
    }
}
