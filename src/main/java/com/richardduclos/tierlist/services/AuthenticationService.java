package com.richardduclos.tierlist.services;

import com.richardduclos.tierlist.dto.Authentication;
import com.richardduclos.tierlist._class.AuthenticationResponse;
import com.richardduclos.tierlist.dto.Register;
import com.richardduclos.tierlist.entities.Role;
import com.richardduclos.tierlist.entities.User;
import com.richardduclos.tierlist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(Register request) {
        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPlainPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var claims = jwtService.getExtraClaims(user);
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(Authentication request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var claims = jwtService.getExtraClaims(user);
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
