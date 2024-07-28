package com.masjid.crm.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserRequest {

    private String email;

    private String password;

    private String phoneNumber;

    private String username;

    private Long roleId;

    private String name;

    private Long id;

    private Boolean active;
}

