package com.waal.controller;

import com.waal.common.response.ApiResponse;
import com.waal.dto.image.ImageUploadResponse;
import com.waal.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Image", description = "이미지 업로드 API")
@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;

    @Operation(summary = "이미지 업로드")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ImageUploadResponse> upload(
            @AuthenticationPrincipal Long userId,
            @RequestPart MultipartFile file,
            @RequestParam(defaultValue = "general") String folder) {
        return ApiResponse.ok(s3Service.upload(file, folder));
    }
}
