package com.maverickstube.maverickshub.dtos.request;

import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateMediaRequest {

    private String description;
    private Category category;
}
