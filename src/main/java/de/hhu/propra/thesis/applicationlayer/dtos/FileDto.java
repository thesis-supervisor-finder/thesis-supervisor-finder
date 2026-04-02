package de.hhu.propra.thesis.applicationlayer.dtos;

import java.time.OffsetDateTime;

public record FileDto(
    long ownerGithubUserId,
    Long topicId,
    String title,
    String description,
    String originalFilename,
    String storedFilename,
    String contentType,
    String fileExtension,
    long sizeBytes,
    OffsetDateTime uploadedAt
) {
}
