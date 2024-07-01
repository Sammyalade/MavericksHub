package com.maverickstube.maverickshub.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.maverickstube.maverickshub.dtos.request.UpdateCategoryRequest;
import com.maverickstube.maverickshub.dtos.request.UpdateDescriptionRequest;
import com.maverickstube.maverickshub.dtos.request.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.response.MediaResponse;
import com.maverickstube.maverickshub.dtos.response.UpdateMediaResponse;
import com.maverickstube.maverickshub.dtos.response.UploadMediaResponse;
import com.maverickstube.maverickshub.exceptions.MediaNotFoundException;
import com.maverickstube.maverickshub.exceptions.MediaUpdateFailedException;
import com.maverickstube.maverickshub.exceptions.MediaUploadFailedException;
import com.maverickstube.maverickshub.models.Media;
import com.maverickstube.maverickshub.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class MavericksHubMediaService implements MediaService {

    private final MediaRepository mediaRepository;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private UserService userService;

    @Override
    public UploadMediaResponse upload(UploadMediaRequest request) {
        try {
            Uploader uploader = cloudinary.uploader();
            Map<?, ?> response = uploader.upload(request.getMediaFile().getBytes(), ObjectUtils.asMap(
                    "resource_type", "auto"
            ));
            String url = response.get("url").toString();
            Media media = modelMapper.map(request, Media.class);
            media.setUrl(url);
            mediaRepository.save(media);
            return modelMapper.map(media, UploadMediaResponse.class);
        } catch (IOException e) {
            throw new MediaUploadFailedException("Media Upload failed");
        }
    }

    @Override
    public Media findById(Long id) {
        return mediaRepository.findById(id).orElseThrow(
                ()-> new MediaNotFoundException("Media not found")
        );
    }

    @Override
    public void updateCategory(UpdateCategoryRequest request) {
        Media media = findById(request.getMediaId());
        media.setCategory(request.getCategory());
        mediaRepository.save(media);
    }

    @Override
    public void updateDescription(UpdateDescriptionRequest request) {
        Media media = findById(request.getMediaId());
        media.setDescription(request.getDescription());
        mediaRepository.save(media);
    }

    @Override
    public UpdateMediaResponse updateMedia(Long id, JsonPatch updateMediaRequest) {
        try {
            //1. get target object
            Media media = findById(id);
            //2. covert object from above to JsonNode(use object mapper)
            ObjectMapper mapper = new ObjectMapper();
            JsonNode mediaNode = mapper.convertValue(media, JsonNode.class);
            //3. apply jsonPatch to mediaNode
            mediaNode = updateMediaRequest.apply(mediaNode);
            //4. convert mediaNode to MediaObject
            media = mapper.convertValue(mediaNode, Media.class);
            mediaRepository.save(media);
            return modelMapper.map(media, UpdateMediaResponse.class);
        } catch (JsonPatchException e) {
            throw new MediaUpdateFailedException(e.getMessage());
        }
    }

    @Override
    public List<MediaResponse> getMediaFor(Long userId) {
        List<Media> medias = mediaRepository.findAllMediaFor(userId);
        return medias.stream()
                .map(m->modelMapper.map(m, MediaResponse.class))
                .toList();
    }

    @Override
    public void save(Media media) {
        mediaRepository.save(media);
    }


}
