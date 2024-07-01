package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dtos.request.LikeMediaRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class LikeServiceTest {

    @Autowired
    private LikeService likeService;
    @Autowired
    private MediaService mediaService;

    @Test
    @DisplayName("Test that user can like media")
    public void testLikeService() {
        LikeMediaRequest request = new LikeMediaRequest();
        request.setUserId(200L);
        request.setMediaId(100L);
        likeService.like(request);
        assertThat(mediaService.findById(100L).getNumberOfLikes()).isEqualTo(1L);
    }
}
