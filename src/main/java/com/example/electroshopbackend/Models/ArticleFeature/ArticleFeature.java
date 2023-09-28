package com.example.electroshopbackend.Models.ArticleFeature;

import com.example.electroshopbackend.Models.Article.Article;
import com.example.electroshopbackend.Models.Feature.Feature;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "article_feature")
public class ArticleFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_feature_id", nullable = false)
    private Long articleFeatureId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId", referencedColumnName = "article_id", nullable = false)
    private Article article;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "featureId", referencedColumnName = "feature_id", nullable = false)
    private Feature feature;

    @Column(name = "value", nullable = false, length=128)
    private String value;
}
