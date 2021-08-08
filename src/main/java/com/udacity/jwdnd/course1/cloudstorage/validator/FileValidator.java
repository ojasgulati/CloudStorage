package com.udacity.jwdnd.course1.cloudstorage.validator;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    private final FileService fileService;

    public FileValidator(FileService fileService) {
        this.fileService = fileService;
    }

    public String validateFileUpload(MultipartFile file) {
        if (file.getSize() == 0)
            return "No file selected";
        if (file.getSize() > 65535) //BLOB MAX SIZE
            return "File size too large";
        if (fileService.isFileNameAlreadyPresent(file.getOriginalFilename()))
            return "File already present";

        return null;
    }
}
