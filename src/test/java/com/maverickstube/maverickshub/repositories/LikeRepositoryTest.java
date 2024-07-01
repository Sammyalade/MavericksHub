package com.maverickstube.maverickshub.repositories;

import com.maverickstube.maverickshub.models.Like;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testGetLikeById() {
        List<Like> like = likeRepository.findByMediaId(100L);
        assertThat(like.size()).isEqualTo(2);
    }

}