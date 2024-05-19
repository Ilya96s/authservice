package com.example.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppError {

    private Integer statusCode;

    private String message;
}
