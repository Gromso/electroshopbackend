package com.example.electroshopbackend.Models.Feature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    List<Feature> findByArticleFeaturesArticleArticleId(Long articleId);
    List<Feature> findByArticleFeaturesArticleName(String name);
}
