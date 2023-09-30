package com.example.electroshopbackend.Models.CartArticle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartArticleRepository extends JpaRepository<CartArticle, Long> {


    Optional<CartArticle> findCartArticleByCartCartIdAndArticleArticleId(Long cartId, Long articleId);

    List<CartArticle> findCartArticleByCartCartId(Long cartId);

}
