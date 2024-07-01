package com.maverickstube.maverickshub.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LikeMediaRequest {

    private Long mediaId;
    private Long userId;

}
