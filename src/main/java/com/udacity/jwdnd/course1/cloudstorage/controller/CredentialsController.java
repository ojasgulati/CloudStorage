package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/home/credentials")
public class CredentialsController {

    final CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @GetMapping("/{credentialId}")
    public Credential getCredential(@PathVariable("credentialId") int credentialId) {
        return credentialsService.getCredentialById(credentialId);
    }


    @PostMapping
    public String createNewCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, Model model) {
        if (Objects.isNull(credentialForm.getCredentialId()))
            credentialsService.createCredential(credentialForm);
        else
            credentialsService.updateCredential(credentialForm.getCredentialId(), credentialForm);
        return "result";
    }

    @PostMapping("/deletecredential/{credentialId}")
    public String deleteNewCredential(@PathVariable("credentialId") int credentialId) {
        credentialsService.deleteCredential(credentialId);
        return "result";
    }
}
