package com.maverickstube.maverickshub.utils;

import com.maverickstube.maverickshub.dtos.request.UploadMediaRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.maverickstube.maverickshub.models.Category.ACTION;

public class TestUtils {

    public static final String TEST_VIDEO_LOCATION = "C:\\Users\\DELL\\Desktop\\maverickshub\\vid.mp4";

    public static UploadMediaRequest buildUploadMediaRequest(InputStream inputStream) throws IOException {
        UploadMediaRequest request = new UploadMediaRequest();
        MultipartFile multipartFile = new MockMultipartFile
                ("vid", "vid.mp4", "video/mp4", inputStream);
        request.setMediaFile(multipartFile);
        request.setCategory(ACTION);
        return request;
    }
}
