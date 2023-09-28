package com.example.electroshopbackend.Models.Feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feature")
public class FeatureController {

    @Autowired
    private FeatureRepository featureRepository;

    @GetMapping("/features/{articleId}")
    public List<Feature> f (@PathVariable Long articleId){

        return featureRepository.findByArticleFeaturesArticleArticleId(articleId);

    }

    @GetMapping("/featuress/{name}")
    public List<Feature> ff (@PathVariable String name){
        return featureRepository.findByArticleFeaturesArticleName(name);

    }

}
