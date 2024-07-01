package com.maverickstube.maverickshub.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.maverickstube.maverickshub.dtos.request.UpdateCategoryRequest;
import com.maverickstube.maverickshub.dtos.request.UpdateDescriptionRequest;
import com.maverickstube.maverickshub.dtos.request.UpdateMediaRequest;
import com.maverickstube.maverickshub.dtos.request.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.response.MediaResponse;
import com.maverickstube.maverickshub.dtos.response.UpdateMediaResponse;
import com.maverickstube.maverickshub.dtos.response.UploadMediaResponse;
import com.maverickstube.maverickshub.models.Media;

import java.util.List;

public interface MediaService {

    UploadMediaResponse upload(UploadMediaRequest request);

    Media findById(Long id);

    void updateCategory(UpdateCategoryRequest request);

    void updateDescription(UpdateDescriptionRequest request);

    UpdateMediaResponse updateMedia(Long id, JsonPatch updateMediaRequest);

    List<MediaResponse> getMediaFor(Long userId);

    void save(Media media);
}
