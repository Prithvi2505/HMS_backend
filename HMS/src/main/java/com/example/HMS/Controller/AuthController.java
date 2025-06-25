package com.example.HMS.Controller;

import com.example.HMS.DTO.LoginRequestDTO;
import com.example.HMS.DTO.LoginResponseDTO;
import com.example.HMS.Entity.AuthUserDetails;
import com.example.HMS.Service.AuthUserDetailsService;
import com.example.HMS.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUserDetailsService userDetailsService;

    /**
     * Health check endpoint
     */
    @GetMapping("/login")
    public String healthCheck() {
        return "Authentication API is working";
    }

    /**
     * Login endpoint: accepts email + password,
     * returns token with email, role, and user ID
     */
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            // Authenticate email and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Load user details (including role and id)
            AuthUserDetails userDetails = (AuthUserDetails) userDetailsService
                    .loadUserByUsername(loginRequest.getEmail());

            // Generate JWT with all 3 fields
            String token = jwtUtil.generateToken(
                    userDetails.getUsername(),    // email
                    userDetails.getRole(),        // role
                    userDetails.getUserId()       // id
            );

            // Return response with token and user info
            return new LoginResponseDTO(token, userDetails.getUserId(), userDetails.getRole());

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
