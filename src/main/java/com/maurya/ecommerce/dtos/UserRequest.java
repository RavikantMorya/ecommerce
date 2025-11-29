package com.maurya.ecommerce.dtos;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private  String password;
    private String email;
    private String mobile;
    private AddressDTO address;

}
