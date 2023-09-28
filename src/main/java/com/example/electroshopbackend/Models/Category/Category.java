package com.example.electroshopbackend.Models.Category;

import com.example.electroshopbackend.Models.Article.Article;
import com.example.electroshopbackend.Models.Feature.Feature;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;



    private String name;

    @Column(name = "imagePath", nullable = false)
    private String imagePath;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Article> articles;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Feature> features;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCategoryId" , referencedColumnName = "category_id")
    private Category parentCategory;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentCategory")
    private Set<Category> childCategories;


}
