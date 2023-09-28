package com.example.electroshopbackend.Models.CartArticle;

import com.example.electroshopbackend.Models.Article.Article;
import com.example.electroshopbackend.Models.Cart.Cart;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cart_article")
public class CartArticle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_article_id", nullable=false)
    private Long cartArticleId;


    @JsonBackReference
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "cartId", referencedColumnName = "cart_id", nullable = false)
    private Cart cart;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId", referencedColumnName = "article_id", nullable = false)
    private Article article;


    private int quantity;
}
