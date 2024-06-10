package com.maverickstube.maverickshub.services;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.maverickstube.maverickshub.dtos.request.UpdateCategoryRequest;
import com.maverickstube.maverickshub.dtos.request.UpdateDescriptionRequest;
import com.maverickstube.maverickshub.dtos.request.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.response.MediaResponse;
import com.maverickstube.maverickshub.dtos.response.UpdateMediaResponse;
import com.maverickstube.maverickshub.dtos.response.UploadMediaResponse;
import com.maverickstube.maverickshub.models.Category;
import com.maverickstube.maverickshub.models.Media;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.maverickstube.maverickshub.models.Category.STEP_MOM;
import static com.maverickstube.maverickshub.utils.TestUtils.TEST_VIDEO_LOCATION;
import static com.maverickstube.maverickshub.utils.TestUtils.buildUploadMediaRequest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
public class MediaServiceTest {


    @Autowired
    private MediaService mediaService;

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void uploadMediaTest(){
        String fileLocation = "C:\\Users\\DELL\\Desktop\\maverickshub\\AdobeStock_627246162_Preview.jpeg";
        Path path = Paths.get(fileLocation);
        try(InputStream inputStream = Files.newInputStream(path)){
            UploadMediaRequest request = new UploadMediaRequest();
            MultipartFile multipartFile = new MockMultipartFile
                    ("adobe", "test.txt", "", inputStream);
            request.setMediaFile(multipartFile);
            UploadMediaResponse response = mediaService.upload(request);
            log.info("response --> {}", response);
            assertThat(response).isNotNull();
            assertThat(response.getMediaUrl()).isNotNull();
        } catch (IOException e){
            assertThat(e).isNull();
        }
    }

    @Test
    public void uploadMediaTest2(){
        Path path = Paths.get(TEST_VIDEO_LOCATION);
        try(InputStream inputStream = Files.newInputStream(path)){
            UploadMediaRequest request = buildUploadMediaRequest(inputStream);
            UploadMediaResponse response = mediaService.upload(request);
            log.info("response --> {}", response);
            assertThat(response).isNotNull();
            assertThat(response.getMediaUrl()).isNotNull();
        } catch (IOException e){
            assertThat(e).isNull();
        }
    }


    @Test
    public void testFindMedia(){
        Media media = mediaService.findById(101L);
        assertThat(media).isNotNull();
    }

    @Test
    public void testGetMediaForUser(){
        Long userId = 200L;
        List<MediaResponse> media = mediaService.getMediaFor(userId);
        log.info("media item --> {}", media);
        assertThat(media).hasSize(3);
    }

    @Test
    public void testUpdateCategory(){
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setMediaId(101L);
        request.setCategory(STEP_MOM);
        mediaService.updateCategory(request);
        assertThat(mediaService.findById(101L).getCategory()).isEqualTo(STEP_MOM);
    }

    @Test
    public void testUpdateDescription(){
        UpdateDescriptionRequest request = new UpdateDescriptionRequest();
        request.setMediaId(101L);
        request.setDescription("what is this");
        mediaService.updateDescription(request);
        assertThat(mediaService.findById(101L).getDescription()).isEqualTo("what is this");
    }

    @Test
    @DisplayName("test update media files")
    public void testUpdateMediaFile() throws JsonPointerException {
        Category category = mediaService.findById(103L).getCategory();
        assertThat(category).isNotEqualTo(STEP_MOM);


        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/category"), new TextNode(STEP_MOM.name()))
        );
        JsonPatch updateRequest = new JsonPatch(operations);
        UpdateMediaResponse response = mediaService.updateMedia(103L, updateRequest);
        assertThat(response).isNotNull();
        category = mediaService.findById(103L).getCategory();
        assertThat(category).isEqualTo(STEP_MOM);
    }
}
