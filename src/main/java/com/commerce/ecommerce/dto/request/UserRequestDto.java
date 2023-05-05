package com.commerce.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "role is required")
    private String role;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "confirmPassword is required")
    private String confirmPassword;

    @Min(value = 16, message = "age should be above 16")
    private Integer age;

}

