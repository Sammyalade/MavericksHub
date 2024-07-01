package com.maverickstube.maverickshub.dtos.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class ReactionResponse {

    private UserResponse likedBy;
    private MediaResponse media;
    private String comment;
    private LocalDateTime timeReacted;
}
