package com.marketplace.serviceusers.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class UserDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
