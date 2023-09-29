package com.example.electroshopbackend.Models.Photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {


    @Autowired
    private PhotoService photoService;


    @PostMapping("/add/{articleId}")
    public ResponseEntity<Photo> savePhoto(@RequestParam("file") MultipartFile file,
                                           @PathVariable Long articleId) throws IOException {
       Photo photo  = photoService.savePhotoForArticle(file, articleId);
       return ResponseEntity.ok(photo);
    }

    @DeleteMapping("/delete/{photoId}")
    public void deletePhoto (@PathVariable Long photoId) throws IOException {
        photoService.deletePhotoForArticle(photoId);
    }
}
