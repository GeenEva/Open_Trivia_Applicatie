package com.example.opentriviabackend.mapper;

import com.example.opentriviabackend.dto.clientResponse.OpenTriviaResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriviaMapperTest {

    private final TriviaMapper triviaMapper =
            Mappers.getMapper(TriviaMapper.class);

    private static final String HTML_ENCODED_QUESTION =
            "What is Euler&#039;s first name?";

    private static final String DECODED_QUESTION =
            "What is Euler's first name?";

    private static final String CORRECT_ANSWER = "Leonhard";

    private static final List<String> INCORRECT_ANSWERS = List.of(
            "Lionel",
            "Andrin",
            "Ajan"
    );

    @Test
    void toTriviaQuestion_shouldMapDecodeAndIncludeAllAnswers() {

        var questionDto = new OpenTriviaResponseDto.QuestionDto(
                HTML_ENCODED_QUESTION,
                CORRECT_ANSWER,
                INCORRECT_ANSWERS
        );

        var triviaQuestion = triviaMapper.toTriviaQuestion(questionDto);

        assertNotNull(triviaQuestion.getId());
        assertEquals(DECODED_QUESTION, triviaQuestion.getQuestion());
        assertEquals(CORRECT_ANSWER, triviaQuestion.getCorrectAnswer());

        var allAnswers = triviaQuestion.getAllAnswers();

        assertNotNull(allAnswers);
        assertEquals(4, allAnswers.size());

        assertTrue(allAnswers.contains(CORRECT_ANSWER));
        assertTrue(allAnswers.contains("Lionel"));
        assertTrue(allAnswers.contains("Andrin"));
        assertTrue(allAnswers.contains("Ajan"));
    }

    @Test
    void decode_shouldConvertHtmlEntities() {

        var encodedText = "Euler&#039;s name";

        var decodedText = triviaMapper.decode(encodedText);

        assertEquals("Euler's name", decodedText);
    }

    @Test
    void decode_shouldReturnNull_whenInputIsNull() {
        assertNull(triviaMapper.decode(null));
    }
}