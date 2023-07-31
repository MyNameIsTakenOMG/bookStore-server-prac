package com.example.bookStore.controller;

import com.example.bookStore.configure.CustomAuthenticationProvider;
import com.example.bookStore.dto.AuthenticationRequest;
import com.example.bookStore.dto.AuthenticationResponse;
import com.example.bookStore.dto.UserDTO;
import com.example.bookStore.service.UserService;
import com.example.bookStore.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@Validated
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private UserService userService;
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

    @PostMapping("/register")
    public ResponseEntity<UUID> addUser(@Valid @RequestBody UserDTO userDTO){
        UUID uuid = userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
    }
}
