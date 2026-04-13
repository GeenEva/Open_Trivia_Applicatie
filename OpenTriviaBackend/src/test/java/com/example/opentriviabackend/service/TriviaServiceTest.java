package com.example.opentriviabackend.service;

import com.example.opentriviabackend.client.OpenTriviaClient;
import com.example.opentriviabackend.dto.answer.AnswerRequestDto;
import com.example.opentriviabackend.model.TriviaQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TriviaServiceTest {

    private static final String QUESTION_ID_1 = "ID_111";
    private static final String QUESTION_ID_2 = "ID_222";
    private static final String QUESTION_TEXT_1 = "What is 1+1?";
    private static final String QUESTION_TEXT_2 = "What is 2+2?";
    private static final String ANSWER_TEXT_1 = "Answer text 1";
    private static final String ANSWER_TEXT_2 = "Answer text 2";
    private static final String CORRECT_ANSWER = "Juiste antwoord";
    private static final String WRONG_ANSWER = "Verkeerde antwoord";

    @Mock
    private OpenTriviaClient client;

    @InjectMocks
    private TriviaService service;

    @Test
    void getQuestions_shouldFetchAndReturnQuestions() {

        var questions = List.of(
                createQuestion(QUESTION_ID_1, QUESTION_TEXT_1, ANSWER_TEXT_1),
                createQuestion(QUESTION_ID_2, QUESTION_TEXT_2, ANSWER_TEXT_2)
        );

        when(client.fetchQuestions()).thenReturn(questions);

        var result = service.getQuestions();

        assertEquals(2, result.size());
        verify(client, times(1)).fetchQuestions();
    }

    @ParameterizedTest
    @MethodSource("answerProvider")
    void checkAnswers_shouldEvaluateAnswerCorrectness(String givenAnswer, boolean expectedCorrect) {

        mockSingleQuestion();

        var request = new AnswerRequestDto(QUESTION_ID_1, givenAnswer, false);

        var result = service.checkAnswers(List.of(request));

        assertEquals(1, result.size());
        assertEquals(expectedCorrect, result.getFirst().isWellAnswered());
    }

    static Stream<Arguments> answerProvider() {
        return Stream.of(
                Arguments.of(CORRECT_ANSWER, true),
                Arguments.of(WRONG_ANSWER, false)
        );
    }

    @Test
    void checkAnswers_shouldThrowException_whenQuestionNotFound() {

        when(client.fetchQuestions()).thenReturn(List.of());
        service.getQuestions();

        var request = new AnswerRequestDto("unknown", CORRECT_ANSWER, false);

        var ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.checkAnswers(List.of(request))
        );

        assertTrue(ex.getMessage().contains("Question not found"));
    }


    private void mockSingleQuestion() {
        when(client.fetchQuestions()).thenReturn(List.of(
                createQuestion(QUESTION_ID_1, QUESTION_TEXT_1, CORRECT_ANSWER)));

        service.getQuestions();
    }

    private TriviaQuestion createQuestion(String id, String stringQuestion, String answer) {
        var triviaQuestion = new TriviaQuestion();
        triviaQuestion.setId(id);
        triviaQuestion.setQuestion(stringQuestion);
        triviaQuestion.setCorrectAnswer(answer);
        return triviaQuestion;
    }
}