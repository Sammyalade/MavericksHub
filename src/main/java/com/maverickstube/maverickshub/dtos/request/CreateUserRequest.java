package com.maverickstube.maverickshub.dtos.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {
    private String email;
    private String password;
}
