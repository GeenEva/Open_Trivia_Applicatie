package com.example.opentriviabackend.control;

import com.example.opentriviabackend.dto.answer.AnswerRequestDto;
import com.example.opentriviabackend.dto.answer.AnswerResultDto;
import com.example.opentriviabackend.model.TriviaQuestion;
import com.example.opentriviabackend.service.TriviaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OpenTriviaController.class)
class OpenTriviaControllerTest {

    private static final String BASE_URL = "/api/opentrivia";
    private static final String QUESTIONS_URL = BASE_URL + "/questions";
    private static final String CHECK_URL = BASE_URL + "/checkAnswers";

    private static final String QUESTION_ID_1 = "1";
    private static final String QUESTION_ID_2 = "2";

    private static final String ANSWER_A = "A";
    private static final String ANSWER_B = "B";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TriviaService triviaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getQuestions_shouldReturnList() throws Exception {

        List<TriviaQuestion> mockQuestions = List.of(
                new TriviaQuestion(),
                new TriviaQuestion()
        );

        when(triviaService.getQuestions()).thenReturn(mockQuestions);

        mockMvc.perform(get(QUESTIONS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(triviaService).getQuestions();
    }

    @Test
    void checkAnswers_shouldReturnResults() throws Exception {

        List<AnswerRequestDto> request = List.of(
                new AnswerRequestDto(QUESTION_ID_1, ANSWER_A, true),
                new AnswerRequestDto(QUESTION_ID_2, ANSWER_B, false)
        );

        List<AnswerResultDto> response = List.of(
                new AnswerResultDto(QUESTION_ID_1, "What is 2+2?", true, "4"),
                new AnswerResultDto(QUESTION_ID_2, "What is 3+3?", false, "6")
        );

        when(triviaService.checkAnswers(any())).thenReturn(response);

        mockMvc.perform(post(CHECK_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].questionId").value(QUESTION_ID_1))
                .andExpect(jsonPath("$[0].isWellAnswered").value(true))
                .andExpect(jsonPath("$[0].correctAnswer").value("4"));

        verify(triviaService).checkAnswers(any());
    }
}