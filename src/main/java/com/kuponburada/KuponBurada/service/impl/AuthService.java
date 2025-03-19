package com.kuponburada.KuponBurada.service.impl;

import com.kuponburada.KuponBurada.dto.request.auth.AuthRequest;
import com.kuponburada.KuponBurada.dto.request.auth.RefreshTokenRequest;
import com.kuponburada.KuponBurada.dto.request.auth.RegisterRequest;
import com.kuponburada.KuponBurada.dto.response.auth.AuthResponse;
import com.kuponburada.KuponBurada.dto.response.auth.UserDto;
import com.kuponburada.KuponBurada.entity.Role;
import com.kuponburada.KuponBurada.repository.UserRepository;
import com.kuponburada.KuponBurada.entity.User;
import com.kuponburada.KuponBurada.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthResponse register(RegisterRequest request) {
        var exists = userRepository.existsByUsername(request.getEmail());

        if (exists) {
            throw new RuntimeException("User already exists");
        }

        var user = User.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        
        userRepository.save(user);

        var userDto = modelMapper.map(user, UserDto.class);


        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        
        return new AuthResponse(accessToken, refreshToken, userDto);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        var user = userRepository.findByUsername(request.getEmail())
                .orElseThrow();
        var userDto = modelMapper.map(user, UserDto.class);

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        
        return new AuthResponse(accessToken, refreshToken, userDto);
    }
    
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        final String refreshToken = request.getRefreshToken();
        final String username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            var user = userRepository.findByUsername(username)
                    .orElseThrow();
            var userDto = modelMapper.map(user, UserDto.class);

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateAccessToken(user);
                return new AuthResponse(accessToken, refreshToken, userDto);
            }
        }
        
        throw new RuntimeException("Refresh token is invalid");
    }
}