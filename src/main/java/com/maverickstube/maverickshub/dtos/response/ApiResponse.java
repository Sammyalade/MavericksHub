package com.maverickstube.maverickshub.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ApiResponse {

    private String message;
    private boolean isSuccessful;
}
