package com.imagebase.images.controllers;

import com.imagebase.images.domain.Images;
import com.imagebase.images.domain.User;
import com.imagebase.images.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Access;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private ImagesRepository imagesRepository;

    @Value("${upload.path}")
    public String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<Images> images = imagesRepository.findAll();
        model.put("images", images);
        return "main";
    }

    @PostMapping("/main")
    public String addImage(
            @AuthenticationPrincipal User user,
            @RequestParam("image") MultipartFile image,
                           Map<String, Object> model
    ) throws IOException {
        Images userImage = new Images();

        if (image != null && !image.getOriginalFilename().isEmpty()){
            File uploaDir = new File(uploadPath);
            if (!uploaDir.exists()){
                uploaDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + image.getOriginalFilename();

            image.transferTo(new File(uploadPath + "/" + resultFilename));
            userImage.setUrl(resultFilename);
        }

        userImage.setAuthor(user);
        imagesRepository.save(userImage);

        Iterable<Images> images = imagesRepository.findAll();
        model.put("images", images);

        return "main";
    }

    @PostMapping("/logout")
    public String logout(){
        return "redirect:/";
    }
}
