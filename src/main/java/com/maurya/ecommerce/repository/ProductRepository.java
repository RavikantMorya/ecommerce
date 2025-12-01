package com.maurya.ecommerce.repository;

import com.maurya.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("SELECT p FROM Product p  where p.active=true AND p.quantity>0 and LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<Product> searchProduct(@RequestParam String keyword);
}
