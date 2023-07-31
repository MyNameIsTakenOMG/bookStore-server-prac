package com.example.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private UUID id;
}
