package com.jwt.rest;

import com.jwt.service.JWTService;
import com.test.api.JwtApi;
import com.test.model.UserDetailsAO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

/*
 * @Created 2/11/24
 * @Project springboot-jwt-microservice
 * @User Kumar Padigeri
 */
@Controller
@AllArgsConstructor
public class JWTController implements JwtApi {

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> addUser(UserDetailsAO userDetailsAO) {
        userDetailsAO.setPassword(passwordEncoder.encode(userDetailsAO.getPassword()));
        return new ResponseEntity<>(jwtService.addUserIntoDB(userDetailsAO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getToken(UserDetailsAO user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.getToken(user.getName()), HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }
}
