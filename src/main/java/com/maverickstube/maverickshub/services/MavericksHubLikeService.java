package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dtos.request.LikeMediaRequest;
import com.maverickstube.maverickshub.models.Like;
import com.maverickstube.maverickshub.models.Media;
import com.maverickstube.maverickshub.models.User;
import com.maverickstube.maverickshub.repositories.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MavericksHubLikeService implements LikeService{

    private MediaService mediaService;
    private UserService userService;
    private LikeRepository likeRepository;

    @Override
    public void like(LikeMediaRequest request) {
        Media media = mediaService.findById(request.getMediaId());
        User user = userService.getUserById(request.getUserId());
        media.increaseLikes();
        mediaService.save(media);

        Like like = new Like();

        like.setMediaLiked(media);
        like.setLikedBy(user);
        likeRepository.save(like);
    }
}
