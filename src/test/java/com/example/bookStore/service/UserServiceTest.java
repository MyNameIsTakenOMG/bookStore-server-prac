package com.example.bookStore.service;

import com.example.bookStore.dto.UserDTO;
import com.example.bookStore.model.User;
import com.example.bookStore.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepo userRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Test
    void shouldReturnUserIdWhenCreateUser() {
        UUID id = UUID.randomUUID();
        when(userRepo.save(any())).thenReturn(getUser(id));
        when(modelMapper.map(any(),any())).thenReturn(getUser(id));
        UUID userId = userService.addUser(getUserDTO(id));
        assertThat(userId).isNotNull();
        assertThat(userId).isEqualTo(id);
    }

    private UserDTO getUserDTO(UUID id) {
        return UserDTO.builder()
                .email("email")
                .name("name")
                .password("password")
                .id(id)
                .build();
    }

    private User getUser(UUID id) {
        return User.builder()
                .email("email")
                .name("name")
                .password("password")
                .id(id)
                .build();
    }

    @Test
    void shouldReturnUserDTOWhenEmailProvided() {
        UUID id = UUID.randomUUID();
        when(userRepo.findByEmail(anyString())).thenReturn(getUser(id));
        when(modelMapper.map(any(),any())).thenReturn(getUserDTO(id));
        UserDTO userDTO = userService.getUserByEmail("email");
        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getName()).isEqualTo("username");
    }
    @Test
    void shouldThrowErrorWhenEmailNotExist() {
        UUID id = UUID.randomUUID();
        when(userRepo.findByEmail(anyString())).thenThrow(new RuntimeException("error"));
        assertThatThrownBy(() -> userService.getUserByEmail("email")).isInstanceOf(RuntimeException.class);
    }
}