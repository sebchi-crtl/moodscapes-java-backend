package com.moodscapes.backend.moodscapes.backend.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record EventSharedResponseDTO(
        String id,
        String userId,
        List<String> sharedUSer
) {
}
