package com.danisanga.application.controllers;

import com.danisanga.application.requests.LoginRequest;
import com.danisanga.application.responses.LoginResponse;
import com.danisanga.domain.models.UserModel;
import com.danisanga.infrastructure.token.generator.JwtAuthTokenGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthTokenGenerator jwtAuthTokenGenerator;

    /**
     * Default constructor.
     *
     * @param authenticationManager injected
     * @param jwtAuthTokenGenerator injected
     */
    public AuthController(final AuthenticationManager authenticationManager,
                          final JwtAuthTokenGenerator jwtAuthTokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthTokenGenerator = jwtAuthTokenGenerator;

    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequestWsDTO)  {

        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestWsDTO.getEmail(),
                            loginRequestWsDTO.getPassword()));

            final String email = authentication.getName();
            final UserModel userModel = new UserModel(email, StringUtils.EMPTY);
            final String token = jwtAuthTokenGenerator.generateToken(userModel);
            final LoginResponse loginRes = new LoginResponse(email,token);
            return ResponseEntity.ok(loginRes);

        }catch (final BadCredentialsException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
