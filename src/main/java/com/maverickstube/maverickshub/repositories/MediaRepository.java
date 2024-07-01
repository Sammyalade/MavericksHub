package com.maverickstube.maverickshub.repositories;

import com.maverickstube.maverickshub.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


//@Query("ALTER TABLE Media MODIFY COLUMN numberOfLikes INT NOT NULL DEFAULT 0")
public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query("SELECT m FROM Media m WHERE m.uploader.id=:userId")
    List<Media> findAllMediaFor(Long userId);
}
