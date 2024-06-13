package com.moodscapes.backend.moodscapes.backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {

    private String access;
    private String refresh;
}
