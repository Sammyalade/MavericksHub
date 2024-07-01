package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dtos.request.LikeMediaRequest;

public interface LikeService {
    void like(LikeMediaRequest request);
}
