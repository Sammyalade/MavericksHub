package com.maverickstube.maverickshub.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadMediaResponse {
    private Long mediaId;
    @JsonProperty("media_url")
    private String mediaUrl;
    @JsonProperty("media_description")
    private String description;
    private Category category;
}
