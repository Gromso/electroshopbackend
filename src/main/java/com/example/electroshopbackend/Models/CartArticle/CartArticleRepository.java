package com.example.electroshopbackend.Models.CartArticle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartArticleRepository extends JpaRepository<CartArticle, Long> {
}
