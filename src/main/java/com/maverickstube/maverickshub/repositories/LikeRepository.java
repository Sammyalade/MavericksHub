package com.maverickstube.maverickshub.repositories;

import com.maverickstube.maverickshub.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT m FROM Like m WHERE m.mediaLiked.id=:mediaId")
    List<Like> findByMediaId(long mediaId);
}
