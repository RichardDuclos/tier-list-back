package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.dto.Authentication;
import com.richardduclos.tierlist._class.AuthenticationResponse;
import com.richardduclos.tierlist.dto.Register;
import com.richardduclos.tierlist.entities.Role;
import com.richardduclos.tierlist.entities.User;
import com.richardduclos.tierlist.services.AuthenticationService;
import com.richardduclos.tierlist.services.JwtService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class AuthenticationController {


    @Autowired
    private Validator validator; // Autowire the validator

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody Register request
            ) {

        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody Authentication request
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
