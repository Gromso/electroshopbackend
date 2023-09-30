package com.example.electroshopbackend.Models.Cart;

import com.example.electroshopbackend.Models.Orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartByUserUserId(Long userId);

    @Query("SELECT o FROM orders o WHERE o.cart.cartId = :cartId")
    Orders findOrdersByCartId(@Param("cartId") Long cartId);
}
