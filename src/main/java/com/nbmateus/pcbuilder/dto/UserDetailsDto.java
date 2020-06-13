package com.nbmateus.pcbuilder.dto;

public class UserDetailsDto {
    
    private String username;
    private String email;
    private boolean admin;

    public UserDetailsDto(String username, String email, boolean admin) {
        this.username = username;
        this.email = email;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    
    
}