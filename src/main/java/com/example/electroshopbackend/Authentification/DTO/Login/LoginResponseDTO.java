package com.example.electroshopbackend.Authentification.DTO.Login;

import com.example.electroshopbackend.Models.AdminUser.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private User user;
    private String token;
    private String refreshToken;
}
