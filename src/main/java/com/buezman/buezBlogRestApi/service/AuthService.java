package com.buezman.buezBlogRestApi.service;

import com.buezman.buezBlogRestApi.payload.LoginDto;
import com.buezman.buezBlogRestApi.payload.SignUpDto;

public interface AuthService {
    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
}
