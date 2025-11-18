package com.inventory.inventory.Commons.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory.Users.dto.LoginRequestDTO;
import com.inventory.inventory.Users.dto.LoginResponseDTO;
import com.inventory.inventory.Users.dto.RegisterRequestDTO;
import com.inventory.inventory.Users.dto.UserResponseDTO;
import com.inventory.inventory.Users.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody RegisterRequestDTO request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}
