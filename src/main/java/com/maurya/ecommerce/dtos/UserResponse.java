package com.maurya.ecommerce.dtos;

import com.maurya.ecommerce.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private String username;
    private  String password;
    private String email;
    private String mobile;
    private AddressDTO address;
    private UserRole userRole;
}
