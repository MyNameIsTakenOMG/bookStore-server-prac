package com.example.bookStore.configure;

import com.example.bookStore.model.User;
import com.example.bookStore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // fetch user info from db
        String emailInput = authentication.getName();
        String passwordInput = authentication.getCredentials().toString();
        User loadedUser = userRepo.findByEmail(emailInput);
        // if no user found or passwords not matched
        if(loadedUser==null || !passwordEncoder.matches(passwordInput, loadedUser.getPassword())){
            throw new BadCredentialsException("credentials incorrect");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
//        return new UsernamePasswordAuthenticationToken(username,hashedPassword,authorities);
        return new UsernamePasswordAuthenticationToken(loadedUser.getEmail(),null,authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
