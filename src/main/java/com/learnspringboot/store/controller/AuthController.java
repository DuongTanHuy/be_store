package com.learnspringboot.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnspringboot.store.request.LoginRequest;
import com.learnspringboot.store.response.ApiResponse;
import com.learnspringboot.store.response.JwtResponse;
import com.learnspringboot.store.security.jwt.JwtUtils;
import com.learnspringboot.store.security.user.StoreUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        StoreUserDetails userDetails = (StoreUserDetails) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), token);
        return ResponseEntity.ok(new ApiResponse("Login successfully!", jwtResponse));
    }

}
