package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.validator.FileValidator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    final FileService fileService;

    public HomeController(final FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String getHome(final Model model) {
        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

    @PostMapping(path = "/fileupload")
    public String fileUpload(@RequestParam("file") MultipartFile file, final Model model) {
        FileValidator fileValidator = new FileValidator(fileService);
        String error = fileValidator.validateFileUpload(file);
        if (error != null) {
            model.addAttribute("fileError", error);
        } else {
            fileService.uploadFile(file);
        }
        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

    @PostMapping(path = "/deletefile/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, final Model model){
        fileService.deleteFile(fileId);
        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

    @GetMapping(path = "/file/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") int fileId) {

        File file = fileService.getFile(fileId);
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(Long.parseLong(file.getFileSize()))
                .contentType(MediaType.valueOf(file.getContentType()))
                .body(resource);
    }
}
