package com.eruption.eruption.user.dto;


import com.eruption.eruption.user.entity.User;

public record SignUpResponse(String name, String email) {
    public static SignUpResponse from(User user) {
        return new SignUpResponse(user.getName(), user.getEmail());
    }
}
