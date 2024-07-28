package com.masjid.crm.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {

    private String username;

    private String name;

    private String token;

    private String role;

    private Long id;

    private String code;

    private List<String> permissions;

//    private Set<Platform> platforms;

    private String kareflowUrl;
}
