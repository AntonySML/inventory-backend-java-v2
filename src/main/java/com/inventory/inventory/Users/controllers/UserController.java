package com.inventory.inventory.Users.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory.Users.dto.UserResponseDTO;
import com.inventory.inventory.Users.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponseDTO getMyProfile(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        
        String userEmail = userDetails.getUsername();

        return userService.getMyProfile(userEmail);
    }
}
