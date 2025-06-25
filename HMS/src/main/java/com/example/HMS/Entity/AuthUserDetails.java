package com.example.HMS.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class AuthUserDetails implements UserDetails {

    private int userId;
    private String email;
    private String password;
    private String role;

    public AuthUserDetails(String email, String password, String role, int userId) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security requires roles to be prefixed with "ROLE_"
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }



    // These can be used for account expiration/locking logic
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

