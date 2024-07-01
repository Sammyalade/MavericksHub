package com.maverickstube.maverickshub.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReactionRequest {

    private Long media;
    private String comment;
    private Long user;
}
