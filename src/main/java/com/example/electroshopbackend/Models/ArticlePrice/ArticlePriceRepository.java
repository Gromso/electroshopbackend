package com.example.electroshopbackend.Models.ArticlePrice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlePriceRepository extends JpaRepository<ArticlePrice, Long> {

}
