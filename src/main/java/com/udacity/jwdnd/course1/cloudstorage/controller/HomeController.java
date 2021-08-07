package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String getHome(){
        return "home";
    }

    @PostMapping(path = "/fileupload")
    public String fileUpload(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename() + " " + file.getSize() + " " + file.getContentType());
        return "home";
    }
}
