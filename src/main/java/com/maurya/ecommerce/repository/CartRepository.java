package com.maurya.ecommerce.repository;

import com.maurya.ecommerce.model.CartItem;
import com.maurya.ecommerce.model.Product;
import com.maurya.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long> {
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findAllByUser( User user);
}
