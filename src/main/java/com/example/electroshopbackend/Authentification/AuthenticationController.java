package com.example.electroshopbackend.Authentification;


import com.example.electroshopbackend.Authentification.DTO.Login.LoginResponseDTO;
import com.example.electroshopbackend.Authentification.DTO.Login.LoginRequestDTO;
import com.example.electroshopbackend.Authentification.DTO.RefreshTokenDTO.UserRefreshTokenRequestDTO;
import com.example.electroshopbackend.Authentification.DTO.RefreshTokenDTO.UserRefreshTokenResponseDTO;
import com.example.electroshopbackend.Authentification.DTO.Registration.RegistrationRequestDTO;
import com.example.electroshopbackend.Authentification.DTO.Registration.RegistrationResponseDTO;
import com.example.electroshopbackend.JWT.RefreshTokenService;
import com.example.electroshopbackend.Models.AdminUser.User;
import com.example.electroshopbackend.Models.AdminUserToken.AdminUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    private  AuthenticationService authenticationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO body){
        LoginResponseDTO e = authenticationService.login(body.getEmail(), body.getPassword());

        return ResponseEntity.ok(e);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody RegistrationRequestDTO body){
        User u = authenticationService.registerUser(body.getEmail(),
                body.getPassword(),
                body.getForename(),
                body.getSurname(),
                body.getPhoneNumber(),
                body.getPostalAddress());
        return ResponseEntity.ok(u);
    }



    @PostMapping("/user/refresh")
    public ResponseEntity<UserRefreshTokenResponseDTO> refreshToken(@RequestBody UserRefreshTokenRequestDTO body){
      UserRefreshTokenResponseDTO token = refreshTokenService.generateNewTokenByRefreshToken(body.getToken());
      return ResponseEntity.ok(token);
    }
}
