package com.michaelfmnk.aldrin.security;

import java.io.Serializable;



public class JwtAuthenticationRequest implements Serializable {

    private String username;

    private String password;
    public JwtAuthenticationRequest(){
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
