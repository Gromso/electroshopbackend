package com.example.electroshopbackend.Models.Article;

import com.example.electroshopbackend.Models.Category.Category;
import com.example.electroshopbackend.Models.Photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {


}
