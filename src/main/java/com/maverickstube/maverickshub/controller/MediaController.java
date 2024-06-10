package com.maverickstube.maverickshub.controller;

import com.maverickstube.maverickshub.dtos.request.UploadMediaRequest;
import com.maverickstube.maverickshub.services.MediaService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/media")
@AllArgsConstructor
public class MediaController {


    private final MediaService mediaService;

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadMedia(@ModelAttribute UploadMediaRequest uploadMediaRequest) {
        return ResponseEntity.status(CREATED)
                .body(mediaService.upload(uploadMediaRequest));
    }

    @GetMapping
    public ResponseEntity<?> getMediaForUser(@RequestParam Long userId){
        return ResponseEntity.ok(mediaService.getMediaFor(userId));
    }
}
