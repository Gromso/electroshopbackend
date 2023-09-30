package com.example.electroshopbackend.Models.Cart;

import com.example.electroshopbackend.Models.CartArticle.DTO.CartArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {



    @Autowired
    private CartService cartService;


    @GetMapping("")
    public ResponseEntity<Cart> createCart(@AuthenticationPrincipal Jwt principal){
        Long  userId = principal.getClaim("userId");
       Cart activeCart = cartService.getActiveCartByUserId(userId);
       return ResponseEntity.ok(activeCart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addArticleToCart(@RequestBody CartArticleDTO body,
                                                 @AuthenticationPrincipal Jwt principal){
        Long  userId = principal.getClaim("userId");
        Cart cartt = cartService.getActiveCartByUserId(userId);
       Cart cart = cartService.AddArticleToCart(cartt.getCartId(),
                body.getArticleId(), body.getQuantity());
        return ResponseEntity.ok(cart);
    }


}
