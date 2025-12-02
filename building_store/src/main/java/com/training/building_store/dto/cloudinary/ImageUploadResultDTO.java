package com.training.building_store.dto.cloudinary;

@Deprecated
public record ImageUploadResultDTO(
        String url,
        String publicId
) {
}
