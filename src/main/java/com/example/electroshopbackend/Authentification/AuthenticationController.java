package com.example.electroshopbackend.Authentification;


import com.example.electroshopbackend.Authentification.DTO.Login.LoginResponseDTO;
import com.example.electroshopbackend.Authentification.DTO.Login.LoginRequestDTO;
import com.example.electroshopbackend.Authentification.DTO.Registration.RegistrationRequestDTO;
import com.example.electroshopbackend.Authentification.DTO.Registration.RegistrationResponseDTO;
import com.example.electroshopbackend.Models.AdminUser.User;
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
}
