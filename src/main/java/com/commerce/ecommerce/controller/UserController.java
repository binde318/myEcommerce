package com.commerce.ecommerce.controller;

import com.commerce.ecommerce.dto.request.UserRequestDto;
import com.commerce.ecommerce.dto.response.ApiResponse;
import com.commerce.ecommerce.dto.response.UserResponseDto;
import com.commerce.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    //Routing and Validating
    //CRUD

    //Exceptions Handling
    //Validation
    //Uniform Interface --> ApiResponse

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

    @GetMapping(path = "{userId}/user")
    public ResponseEntity<Object> fetchUserById(@PathVariable Long userId) {
        UserResponseDto foundUser = userService.fetchUserById(userId);

//        return ResponseEntity.ok(foundUser);
//        return new ResponseEntity<>(foundUser, HttpStatus.OK);

        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(foundUser);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("user fetch");

        return ResponseEntity.status(200).body(apiResponse);
    }

    @DeleteMapping(path = "{userId}/user")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData("User Deleted");
        apiResponse.setStatusCode("00");

        return ResponseEntity.status(202).body(apiResponse);
    }

    @PutMapping(path = "{userId}/user")
    public ResponseEntity<Object> updateUserById(@PathVariable Long userId, @RequestBody UserRequestDto request) {
        UserResponseDto response = userService.updateUser(userId, request);

        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("updated successfully");

        return ResponseEntity.status(202).body(apiResponse);
    }

}

