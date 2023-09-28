package com.example.electroshopbackend.Models.Feature;

import com.example.electroshopbackend.Models.ArticleFeature.ArticleFeature;
import com.example.electroshopbackend.Models.Category.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "feature")
public class Feature {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false , length = 128)
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArticleFeature> articleFeatures;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "category_id", nullable = false)
    private Category category;

}
