package com.maurya.ecommerce.service;
import com.maurya.ecommerce.dtos.AddressDTO;
import com.maurya.ecommerce.dtos.UserRequest;
import com.maurya.ecommerce.dtos.UserResponse;
import com.maurya.ecommerce.model.Address;
import com.maurya.ecommerce.model.User;
import com.maurya.ecommerce.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.maurya.ecommerce.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromUserRequest(user, userRequest);
        user.setRole(UserRole.CUSTOMER);
        userRepository.save(user);
    }

    public List<UserResponse> fetchUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapUserResponseFromUser)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapUserResponseFromUser);
    }

    public Boolean updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id).map(
                existing->{
                    updateUserFromUserRequest(existing, userRequest);
                    userRepository.save(existing);
                    return true;
                }
        ).orElse(false);
    }

    private UserResponse mapUserResponseFromUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setMobile(user.getMobile());
        if(user.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setCountry(user.getAddress().getCountry());
            address.setZip(user.getAddress().getZip());
            userResponse.setAddress(address);
        }
        return userResponse;
    }
    private void updateUserFromUserRequest(User user,
                                           UserRequest userRequest) {
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setMobile(userRequest.getMobile());
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZip(userRequest.getAddress().getZip());
            user.setAddress(address);
        }
    }
}
