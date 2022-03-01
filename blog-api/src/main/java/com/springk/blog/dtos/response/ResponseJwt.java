package com.springk.blog.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseJwt {
    private String tokenType = "Bearer";
    private String token;
    private long id;
    private String username;
    private String email;
    private List<String> roles;
    public ResponseJwt(String token, long id, String username, String email, List<String> roles){
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
