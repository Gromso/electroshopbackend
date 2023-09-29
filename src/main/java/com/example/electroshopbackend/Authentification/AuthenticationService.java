package com.example.electroshopbackend.Authentification;

import com.example.electroshopbackend.Authentification.DTO.Login.LoginResponseDTO;
import com.example.electroshopbackend.Exception.ApiRequestException;
import com.example.electroshopbackend.JWT.RefreshTokenService;
import com.example.electroshopbackend.JWT.TokenService;
import com.example.electroshopbackend.Models.AdminUser.User;
import com.example.electroshopbackend.Models.AdminUser.UserRepository;
import com.example.electroshopbackend.Models.AdminUserToken.AdminUserToken;
import com.example.electroshopbackend.Models.Role.Role;
import com.example.electroshopbackend.Models.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class AuthenticationService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;


    public User registerUser(String email, String password, String forename, String surname, String phone_number, String postal_address ){
        String encodePassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").orElse(null);
        Set<Role> authority= new HashSet<>();
        authority.add(userRole);

        return userRepository.save(new User(email,

                encodePassword,
                authority,
                forename,
                surname,
                phone_number,
                postal_address));
    }

    public LoginResponseDTO login(String email, String password){
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email,password)
            );
            Long userId = ((User) auth.getPrincipal()).getUserId();
            User user = userRepository.findByUserId(userId);
            String token = tokenService.generateJWToken(userId,user);
            String refreshToken = tokenService.generateRefreshToken(userId,auth);
            refreshTokenService.saveRefreshToken(userId, refreshToken);
            AdminUserToken refreshTokenn = refreshTokenService.findRefreshToken(refreshToken);
            return new LoginResponseDTO(userRepository.findByEmail(email).orElseThrow(() ->
                    new ApiRequestException("User by " + email + " not found ",
                            HttpStatus.NOT_FOUND,
                            -1003)),
                    token,
                    refreshTokenn.getToken());
        }catch(Exception e){
            throw new ApiRequestException(e.getMessage(),
                    HttpStatus.UNAUTHORIZED, -1002);
        }
    }







}