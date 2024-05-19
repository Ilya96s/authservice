package com.example.authservice.controller;

import com.example.authservice.dto.UserDto;
import com.example.authservice.service.TokenService;
import com.example.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public String getToken(@RequestBody UserDto userDto) {
        userService.checkCredentials(userDto.getName(), userDto.getPassword());
        return tokenService.generateToken(userDto.getName());
    }
}
