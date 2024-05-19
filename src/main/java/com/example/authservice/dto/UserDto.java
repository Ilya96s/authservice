package com.example.authservice.dto;

import lombok.Data;

@Data
public class UserDto {

    private String name;

    private String password;

    private String email;

    private String phone;
}
