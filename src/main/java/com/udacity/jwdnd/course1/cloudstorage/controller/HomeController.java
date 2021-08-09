package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    final FileService fileService;
    final NotesService notesService;
    final CredentialsService credentialsService;

    public HomeController(FileService fileService, NotesService notesService, CredentialsService credentialsService) {
        this.fileService = fileService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
    }

    @GetMapping
    public String getHome(final Model model, @ModelAttribute("noteForm") NoteForm note, @ModelAttribute("credentialForm") CredentialForm credentialForm) {
        model.addAttribute("files", fileService.getAllFiles());
        model.addAttribute("notes", notesService.getNotes());
        model.addAttribute("credentials", credentialsService.getCredentials());
        return "home";
    }
}
