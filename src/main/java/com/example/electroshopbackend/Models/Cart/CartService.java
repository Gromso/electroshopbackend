package com.example.electroshopbackend.Models.Cart;

import com.example.electroshopbackend.Exception.ApiRequestException;
import com.example.electroshopbackend.Models.AdminUser.UserRepository;
import com.example.electroshopbackend.Models.Article.Article;
import com.example.electroshopbackend.Models.Article.ArticleService;
import com.example.electroshopbackend.Models.CartArticle.CartArticle;
import com.example.electroshopbackend.Models.CartArticle.CartArticleService;
import com.example.electroshopbackend.Models.Orders.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public final class CartService {

    @Autowired
    private  CartRepository cartRepository;
    @Autowired
    private CartArticleService cartArticleService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    @Autowired
    private EntityManager entityManager;


     /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
    public Cart AddArticleToCart(Long cartId, Long articleId, int quantity){
        Optional<CartArticle> cartArticle =
                cartArticleService.findCartArticleByCartIdAndArticleId(
                        cartId,
                        articleId);

        if(cartArticle.isEmpty()){
            CartArticle newCartArticle = new CartArticle();
            Cart cart = getCartById(cartId);
            Article article = articleService.getArticleByArticleId(articleId);
            newCartArticle.setCart(cart);
            newCartArticle.setArticle(article);
            newCartArticle.setQuantity(quantity);
            cartArticleService.saveCartArticle(newCartArticle);

        }else{
           cartArticleService.updateCartArticleForQuantity(cartArticle.get(), quantity);
        }
        return getCartById(cartId);
    }
     /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
     public Cart getCartById(Long cartId){
        return cartRepository.findById(cartId).orElseThrow(() ->
                new ApiRequestException("e",
                HttpStatus.BAD_REQUEST,
                -1002));
     }
     /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
     public void createNewCartForUser(Long userId){
         Instant instant = Instant.now();
         Date now = Date.from(instant);
         Cart cart = new Cart();
         Cart cartUser = userRepository.findById(userId).map(user ->{
             cart.setUser(user);
             cart.setCreatedAtCart(now);
             return cartRepository.save(cart);
         }).orElseThrow(() -> new ApiRequestException("save failed", HttpStatus.NOT_FOUND, -1011));
          cartRepository.save(cartUser);
     }
     /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
     public Cart getActiveCartByUserId(Long userId) {
         Cart cart = getLastActiveCartByUserId(userId);
         if (cart != null) {
             Long cartId = cart.getCartId();
             Orders orders = cartRepository.findOrdersByCartId(cartId);
             if (orders != null) {
                 createNewCartForUser(userId);
             }
             return cart;
         } else {
             createNewCartForUser(userId);
             return getLastActiveCartByUserId(userId);
         }
     }
     /***********************************************************
     * **********************************************************
     * **********************************************************
     * **********************************************************/
    private Cart getLastActiveCartByUserId(Long userId) {
        String jpql = "SELECT c FROM cart c " +
                "WHERE c.user.userId = :userId " +
                "ORDER BY c.createdAtCart DESC";
        TypedQuery<Cart> query = entityManager.createQuery(jpql, Cart.class);
        query.setParameter("userId", userId);
        query.setMaxResults(1);
        List<Cart> carts = query.getResultList();
        if (carts.isEmpty()) {
            return null;
        } else {
            return carts.get(0);
        }
    }




}