package com.example.opentriviabackend.dto.clientResponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//De response die oorspronkelijk vanuit de client terugkomt
@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenTriviaResponseDto(
        @JsonProperty("response_code") int responseCode,
        List<QuestionDto> results
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record QuestionDto(
            String question,

            @JsonProperty("correct_answer")
            String correctAnswer,

            @JsonProperty("incorrect_answers")
            List<String> incorrectAnswers
    ) {}
}