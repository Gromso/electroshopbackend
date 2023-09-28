package com.example.electroshopbackend.Models.ArticleFeature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFeatureRepository extends JpaRepository<ArticleFeature, Long> {

}
