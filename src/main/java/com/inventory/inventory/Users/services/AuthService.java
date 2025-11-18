package com.inventory.inventory.Users.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventory.inventory.Config.JwtService;
import com.inventory.inventory.Users.dto.LoginRequestDTO;
import com.inventory.inventory.Users.dto.LoginResponseDTO;
import com.inventory.inventory.Users.dto.RegisterRequestDTO;
import com.inventory.inventory.Users.dto.UserResponseDTO;
import com.inventory.inventory.Users.entity.User;
import com.inventory.inventory.Users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserResponseDTO register(RegisterRequestDTO request) {
        
        var existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new RuntimeException("El correo que ya se encuentra registrado");
        }
        
        User userNew = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
        User userSaved = userRepository.save(userNew);

        return UserResponseDTO.builder()
            .id(userSaved.getId())
            .name(userSaved.getName())
            .email(userSaved.getEmail())
            .build();
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de autenticar"));

        String token = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
            .token(token)
            .build();
    }
}