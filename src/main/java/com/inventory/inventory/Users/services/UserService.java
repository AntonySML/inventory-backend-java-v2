package com.inventory.inventory.Users.services;

import org.springframework.stereotype.Service;

import com.inventory.inventory.Users.dto.UserResponseDTO;
import com.inventory.inventory.Users.entity.User;
import com.inventory.inventory.Users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO getMyProfile(String userEmail){

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado en el token"));
        
        return UserResponseDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .build();

    }

}
