package com.example.electroshopbackend.Models.Article;

import com.example.electroshopbackend.Models.ArticleFeature.ArticleFeature;
import com.example.electroshopbackend.Models.ArticlePrice.ArticlePrice;
import com.example.electroshopbackend.Models.CartArticle.CartArticle;
import com.example.electroshopbackend.Models.Category.Category;
import com.example.electroshopbackend.Models.Photo.Photo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "article")
public class Article {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "excerpt" , nullable = false, length = 255)
    private String excerpt;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private final ArticleStatusEnum status = ArticleStatusEnum.available;

    @Column(name = "is_promoted", nullable = false, columnDefinition = "TINYINT")
    private byte isPromoted;

    @JsonManagedReference
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CartArticle> cartArticles;

    @JsonManagedReference
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ArticlePrice> articlePrices;

    @JsonManagedReference
    @OneToMany(mappedBy= "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArticleFeature> articleFeatures;

    @JsonManagedReference
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Photo> photos;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "category_id", nullable = false)
    private Category category;


}
