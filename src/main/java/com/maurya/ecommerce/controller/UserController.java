package com.maurya.ecommerce.controller;
import com.maurya.ecommerce.dtos.UserRequest;
import com.maurya.ecommerce.dtos.UserResponse;
import com.maurya.ecommerce.model.User;
import com.maurya.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public String addUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
        return "User added successfully";
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
        Boolean updateUser = userService.updateUser(id,userRequest);
        if (updateUser == false){
            return "User not found";
        }
        return "User updated successfully";
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        return userService.fetchUsers();
    }
}
