package com.maverickstube.maverickshub.dtos.request;

import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCategoryRequest {

    private long mediaId;
    private Category category;

}
