package com.commerce.ecommerce.service;

import com.commerce.ecommerce.dto.request.UserRequestDto;
import com.commerce.ecommerce.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto fetchUserById(Long userId);

    UserResponseDto updateUser(Long userId, UserRequestDto request);

    void deleteUser(Long userId);
}

