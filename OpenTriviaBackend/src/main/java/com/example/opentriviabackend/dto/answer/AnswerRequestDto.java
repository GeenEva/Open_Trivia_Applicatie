package com.example.opentriviabackend.dto.answer;

public record AnswerRequestDto(
        String questionId,
        String givenAnswer
) {}