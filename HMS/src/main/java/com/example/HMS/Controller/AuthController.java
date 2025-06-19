package com.example.HMS.Controller;

import com.example.HMS.DTO.LoginRequestDTO;
import com.example.HMS.DTO.LoginResponseDTO;
import com.example.HMS.Service.AuthUserDetailsService;
import com.example.HMS.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthUserDetailsService userDetailsService;

    @GetMapping("/login")
    public String HealthChecck() {
        return "its working";
    }

    @PostMapping("/login")

    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername(),loginRequest.getRole());
        int userId = userDetailsService.getUserIdFromRepo(loginRequest);
        return new LoginResponseDTO(token, userId, loginRequest.getRole());
    }


}
