package com.example.bookStore.service;

import com.example.bookStore.dto.UserDTO;
import com.example.bookStore.model.User;
import com.example.bookStore.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    public UUID addUser(UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID());

        User newUser = userRepo.save(user);
        return newUser.getId();
    }
    public UserDTO getUserByEmail(String email){
        User user = userRepo.findByEmail(email);
        if(user == null){
            throw new RuntimeException("user not exists");
        }
        return modelMapper.map(user, UserDTO.class);
    }
}
