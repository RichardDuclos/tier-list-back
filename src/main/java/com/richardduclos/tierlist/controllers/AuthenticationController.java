package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist._class.auth.AuthenticationRequest;
import com.richardduclos.tierlist._class.auth.AuthenticationResponse;
import com.richardduclos.tierlist._class.auth.RegisterRequest;
import com.richardduclos.tierlist.entities.Role;
import com.richardduclos.tierlist.entities.User;
import com.richardduclos.tierlist.services.AuthenticationService;
import com.richardduclos.tierlist.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/me")
    public User me (
        @RequestHeader(name="Authorization") String authHeader
    ) {
        String jwt = jwtService.extractJwtFromAuthorization(authHeader);
        var claims = jwtService.extractAllClaims(jwt);
        User user = new User();
        user.setId(UUID.fromString(claims.get("id").toString()));
        user.setUsername(claims.getSubject());
        user.setRole(Role.valueOf(claims.get("role").toString()));
        return user;
    }
}
