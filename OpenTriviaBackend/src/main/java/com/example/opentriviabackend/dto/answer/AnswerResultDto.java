package com.example.opentriviabackend.dto.answer;

public record AnswerResultDto(
        String questionId,
        String question,
        boolean isWellAnswered,
        String correctAnswer
) {}