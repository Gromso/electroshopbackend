package com.example.electroshopbackend.Models.Photo;

import com.example.electroshopbackend.Exception.ApiRequestException;
import com.example.electroshopbackend.Models.Article.Article;
import com.example.electroshopbackend.Models.Article.ArticleRepository;
import com.example.electroshopbackend.Models.Photo.GeneratePhotoFile.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public final class PhotoService {


    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private ArticleRepository articleRepository;



    public List<Photo> getPhotosByArticleId(Long articleId) {
        return photoRepository.findPhotosByArticleArticleId(articleId);
    }

    public Photo savePhotoForArticle(MultipartFile file, Long articleId) throws IOException {
        if (file.isEmpty()) {
            throw new ApiRequestException("File is Empty", HttpStatus.BAD_REQUEST, -1002);
        }
        String mimeType = new Tika().detect(file.getInputStream());

        if (!mimeType.equals("image/jpeg") && !mimeType.equals("image/png")) {
            throw new ApiRequestException("just JPG and PNG images are supported", HttpStatus.NOT_ACCEPTABLE, -1005);
        }
        String imageName = GenerateImageName.generateFileName(Objects.requireNonNull(file.getOriginalFilename()));

        Path originalFilePath = Paths.get(uploadDir+"normalImages/", imageName);
        Path smallFilePath = Paths.get(uploadDir + "small/", imageName);
        Path thumbnailFilePath = Paths.get(uploadDir + "thumb/", imageName);
        try {
            FileUploader uploadMainImage = new FileUploaderImpl();
            uploadMainImage.uploadFile(originalFilePath, file);
            ImageProcessor uploadSmallImage = new SmallImageProcessor();
            ImageProcessor uploadThumbImage = new ThumbnailImageProcessor();
            uploadSmallImage.processImage(originalFilePath, smallFilePath);
            uploadThumbImage.processImage(originalFilePath,thumbnailFilePath);

        } catch (IOException e) {
            throw new ApiRequestException("File not found", HttpStatus.BAD_REQUEST , -1002);
        }
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ApiRequestException("Article with id " + articleId + " not found", HttpStatus.BAD_REQUEST, -1002));
        Photo photo = new Photo();
        photo.setArticle(article);
        photo.setImagePath(imageName);
        return photoRepository.save(photo);
    }

    public void deletePhotoForArticle(final Long photoId) throws IOException {
        Photo photoId2 = photoRepository.findById(photoId).orElseThrow(() ->
                new ApiRequestException("Photo by id " + photoId + " not found", HttpStatus.NOT_FOUND, -1006));
        try {
            String path = "D:\\java_programs\\Java-App\\electronicshop\\storage\\photos";
            Path originalFilePath = Paths.get(path + "\\normalImages\\" + photoId2.getImagePath());
            Path smallFilePath = Paths.get(path + "\\small\\" + photoId2.getImagePath());
            Path thumbnailFilePath = Paths.get(path + "\\thumb\\" + photoId2.getImagePath());
            Files.delete(originalFilePath);
            Files.delete(smallFilePath);
            Files.delete(thumbnailFilePath);
        }catch (NoSuchFileException e){
            e.getMessage();
            throw new ApiRequestException(e.getMessage(), HttpStatus.BAD_REQUEST, -1002);
        }
        deletePhoto(photoId);
    }

    private void deletePhoto (final Long photoId){
        photoRepository.findById(photoId).orElseThrow(()->
                new ApiRequestException("Photo by ID " + photoId + " not found",HttpStatus.NOT_FOUND,-1002 ));
        photoRepository.deleteById(photoId);
        throw new ApiRequestException("Photo by ID " + photoId + " successfully deleted", HttpStatus.OK, -1010);
    }



}
