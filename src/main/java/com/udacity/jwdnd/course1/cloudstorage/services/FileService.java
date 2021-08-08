package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    final UserService userService;
    final FileMapper fileMapper;

    public FileService(final UserService userService, final FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public void uploadFile(MultipartFile documentFile) {
        try {
            final int userId = userService.getCurrentUserId();
            final String name = documentFile.getOriginalFilename();
            final String fileSize = String.valueOf(documentFile.getSize());
            final String contentType = documentFile.getContentType();
            File file = new File(name, contentType, fileSize, userId, documentFile.getBytes());
            fileMapper.insertFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFileNameAlreadyPresent(String fileName) {
        final int userId = userService.getCurrentUserId();
        return fileMapper.getFileByName(fileName, userId) != null;
    }

    public List<File> getAllFiles() {
        final int userId = userService.getCurrentUserId();
        return fileMapper.getFiles(userId);
    }

    @Transactional
    public void deleteFile(int fileId) {
        final int userId = userService.getCurrentUserId();
        fileMapper.deleteFile(fileId, userId);
    }

    public File getFile(int fileId) {
        final int userId = userService.getCurrentUserId();
        return fileMapper.getFileById(fileId, userId);
    }
}
