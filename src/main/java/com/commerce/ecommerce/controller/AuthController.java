package com.commerce.ecommerce.controller;

import com.commerce.ecommerce.dto.request.LogInRequest;
import com.commerce.ecommerce.dto.request.UserRequestDto;
import com.commerce.ecommerce.dto.response.ApiResponse;
import com.commerce.ecommerce.dto.response.TokenResponse;
import com.commerce.ecommerce.dto.response.UserResponseDto;
import com.commerce.ecommerce.service.UserService;
import com.commerce.ecommerce.utils.AppUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping(path = "auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    //Login

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LogInRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));


        if (authentication.isAuthenticated()) {
            TokenResponse response = AppUtil.generateToken(authentication);
            return ResponseEntity.status(200).body(ApiResponse.builder().statusCode("00").data(response).message("Authenticated").build());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> createAccount(@RequestBody @Valid UserRequestDto request) {
        log.info("create user call for name: {}", request.getName());
        UserResponseDto response =  userService.createUser(request);

        //Using constructor
        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("created user successfully");

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
