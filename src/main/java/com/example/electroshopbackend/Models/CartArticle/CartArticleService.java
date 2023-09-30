package com.example.electroshopbackend.Models.CartArticle;

import com.example.electroshopbackend.Exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class CartArticleService {

    @Autowired
    private CartArticleRepository cartArticleRepository;


     /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
    public void saveCartArticle(CartArticle cartArticle){
        cartArticleRepository.save(cartArticle);
    }
     /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
     public  Optional<CartArticle> findCartArticleByCartIdAndArticleId(Long cartId, Long articleId){
        return cartArticleRepository.findCartArticleByCartCartIdAndArticleArticleId(cartId,articleId);
    }
    /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
    public void updateCartArticleForQuantity(CartArticle cartArticle,
                                             int quantity) {

        cartArticle.setQuantity(cartArticle.getQuantity() + quantity);
        cartArticleRepository.save(cartArticle);
    }
    /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
     public List<CartArticle> getListCartArticle(Long cartId){
         return cartArticleRepository.findCartArticleByCartCartId(cartId);
     }
    /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
     public void deleteCartArticleForArticle(Long cartId, Long articleId){
        Optional<CartArticle> cartArticle = cartArticleRepository.findCartArticleByCartCartIdAndArticleArticleId(cartId,articleId);
        if(cartArticle.isEmpty()){
            throw new ApiRequestException("Article by ID " + articleId + " not found", HttpStatus.NOT_FOUND, -1011);
        }
        cartArticleRepository.delete(cartArticle.get());
    }
}
