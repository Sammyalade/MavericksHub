package com.maverickstube.maverickshub.dtos.request;

import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@ToString
public class UploadMediaRequest {

    private MultipartFile mediaFile;
    private String description;
    private Category category;
    private Long userId;
}
