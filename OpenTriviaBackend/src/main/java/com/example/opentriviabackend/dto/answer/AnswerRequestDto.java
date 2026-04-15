package com.example.opentriviabackend.dto.answer;

import jakarta.validation.constraints.NotBlank;

public record AnswerRequestDto(
        @NotBlank String questionId,
        @NotBlank String givenAnswer
) {}