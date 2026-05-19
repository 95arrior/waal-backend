package com.waal.dto.image;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUploadResponse {
    private String url;
    private String key;
}
