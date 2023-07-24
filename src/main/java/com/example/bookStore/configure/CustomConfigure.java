package com.example.bookStore.configure;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfigure {

    @Bean
    public ModelMapper createMapper(){
        return new ModelMapper();
    }
}
