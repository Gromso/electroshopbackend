package com.example.electroshopbackend.JWT;

import com.example.electroshopbackend.Models.AdminUser.User;
import com.example.electroshopbackend.Models.AdminUser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    public static final long JWT_ACCESS_TOKEN_EXPIRY = 300L;
    public static final long JWT_REFRESH_TOKEN_EXPIRY = 72000L;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtEncoder jwtEncoder;

    private String generateRefreshJwt(Authentication auth){
        Instant now = Instant.now();
        Long userId = ((User) auth.getPrincipal()).getUserId();
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(JWT_REFRESH_TOKEN_EXPIRY))
                .subject(auth.getName())
                .claim("roles", scope)
                .claim("userId", userId)
                .build();
        return  jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String generateJTW(User user){
        Instant now = Instant.now();
        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(JWT_ACCESS_TOKEN_EXPIRY))
                .subject(user.getUsername())
                .claim("roles",scope)
               .claim("userId", user.getUserId())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }



    public final String generateJWToken(Long userId, User user){
         userRepository.findById(userId).orElseThrow();
          return generateJTW(user);
    }

    public final String generateRefreshToken(Long userId, Authentication auth){
        userRepository.findById(userId).orElseThrow();
        return generateRefreshJwt(auth);
    }





}
