package com.maurya.ecommerce.repository;

import com.maurya.ecommerce.model.Product;
import com.maurya.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserRepository extends  JpaRepository<User,Long> { }
