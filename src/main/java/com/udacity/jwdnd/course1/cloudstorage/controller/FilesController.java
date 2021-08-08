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

@Controller
@RequestMapping("/home/files")
public class FilesController {

    final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "/fileupload")
    public String fileUpload(@RequestParam("file") MultipartFile file, final Model model) {
        FileValidator fileValidator = new FileValidator(fileService);
        String error = fileValidator.validateFileUpload(file);
        if (error != null) {
            model.addAttribute("error", error);
        } else {
            fileService.uploadFile(file);
        }
        return "result";
    }

    @PostMapping(path = "/deletefile/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, final Model model){
        fileService.deleteFile(fileId);
        return "result";
    }

    @GetMapping(path = "/{fileId}")
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
