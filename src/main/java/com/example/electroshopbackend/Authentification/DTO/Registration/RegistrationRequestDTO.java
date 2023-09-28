package com.example.electroshopbackend.Authentification.DTO.Registration;


import lombok.Getter;

@Getter
public class RegistrationRequestDTO {

    private String email;
    private String password;
    private String forename;
    private String surname;
    private String phoneNumber;
    private String postalAddress;

}
