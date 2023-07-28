package com.example.bookStore.controller;

import com.example.bookStore.configure.CustomAuthenticationProvider;
import com.example.bookStore.dto.AuthenticationRequest;
import com.example.bookStore.dto.AuthenticationResponse;
import com.example.bookStore.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
            var userAuthentication = customAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);
            String jwtToken = jwtUtil.generateJwtToken(userAuthentication);
            return ResponseEntity.ok(new AuthenticationResponse("Bearer "+jwtToken));
        }catch (BadCredentialsException ex){
            throw new RuntimeException("credentials are not correct");
        }
    }
}