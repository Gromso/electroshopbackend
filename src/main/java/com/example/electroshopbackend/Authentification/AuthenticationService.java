package com.example.electroshopbackend.Authentification;

import com.example.electroshopbackend.Authentification.DTO.Login.LoginResponseDTO;
import com.example.electroshopbackend.JWT.TokenService;
import com.example.electroshopbackend.Models.AdminUser.User;
import com.example.electroshopbackend.Models.AdminUser.UserRepository;
import com.example.electroshopbackend.Models.Role.Role;
import com.example.electroshopbackend.Models.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {


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
           // Integer userId = ((ApplicationUser) auth.getPrincipal()).getUser_id();
            String token = tokenService.generateJTW(auth);
            //String refreshToken = tokenService.generateRefreshJwt(auth);
           // tokenService.addToken(userId, refreshToken);
          //  UserToken u = tokenService.getTokenByName(refreshToken);
           // String expires_at = u.getExpires_at();

            return new LoginResponseDTO(userRepository.findByEmail(email).orElse(null), token);
        }catch(Exception e){
            throw new IllegalArgumentException();
        }
    }
}
