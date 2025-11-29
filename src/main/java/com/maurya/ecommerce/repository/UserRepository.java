package com.maurya.ecommerce.repository;

import com.maurya.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends  JpaRepository<User,Long> {

}
