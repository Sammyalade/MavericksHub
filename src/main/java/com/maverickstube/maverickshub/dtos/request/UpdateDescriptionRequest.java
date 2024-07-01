package com.maverickstube.maverickshub.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDescriptionRequest {

    private Long mediaId;
    private String description;
}
