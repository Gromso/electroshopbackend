package com.example.electroshopbackend.Models.Article;

import com.example.electroshopbackend.Exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public final class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;



    public Article getArticleByArticleId(Long articleId){
        return articleRepository.findById(articleId).orElseThrow(() -> new ApiRequestException("", HttpStatus.BAD_REQUEST, -1002));
    }
}
