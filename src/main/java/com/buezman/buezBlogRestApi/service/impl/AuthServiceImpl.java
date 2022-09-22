package com.buezman.buezBlogRestApi.service.impl;

import com.buezman.buezBlogRestApi.entity.Role;
import com.buezman.buezBlogRestApi.entity.User;
import com.buezman.buezBlogRestApi.exception.CredentialAlreadyExistsException;
import com.buezman.buezBlogRestApi.exception.ResourceNotFoundException;
import com.buezman.buezBlogRestApi.payload.LoginDto;
import com.buezman.buezBlogRestApi.payload.SignUpDto;
import com.buezman.buezBlogRestApi.repository.RoleRepository;
import com.buezman.buezBlogRestApi.repository.UserRepository;
import com.buezman.buezBlogRestApi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public String signUp(SignUpDto signUpDto) {

        validateSignupCredentials(signUpDto);

        Role role = roleRepository.findByName("ROLE_USER");

        User user = User.builder()
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .username(signUpDto.getUsername())
                .roles(Collections.singleton(role))
                .build();

        userRepository.save(user);

        return "Registration successful";

    }

    @Override
    public String login(@Valid @RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User login successful";
    }

    private void validateSignupCredentials(SignUpDto signUpDto) {
        String email = signUpDto.getEmail();
        String username = signUpDto.getUsername();
        if (userRepository.existsByEmail(email))
            throw new CredentialAlreadyExistsException("Email", email);
        else if (userRepository.existsByUsername(signUpDto.getUsername()))
            throw new CredentialAlreadyExistsException("Username", username);
    }
}
