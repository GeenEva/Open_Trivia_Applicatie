package com.example.opentriviabackend.client;

import com.example.opentriviabackend.dto.response.OpenTriviaResponseDto;
import com.example.opentriviabackend.mapper.TriviaMapper;
import com.example.opentriviabackend.model.TriviaQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenTriviaClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TriviaMapper mapper;

    @InjectMocks
    private OpenTriviaClient client;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void fetchQuestions_shouldReturnMappedQuestions_fromApiResponse() throws Exception {

        var response = loadResponse("original.json");

        var expectedQuestions = List.of(createQuestion());

        when(restTemplate.getForObject(anyString(), eq(OpenTriviaResponseDto.class)))
                .thenReturn(response);

        when(mapper.toTriviaQuestions(response.results()))
                .thenReturn(expectedQuestions);

        var result = client.fetchQuestions();

        assertEquals(expectedQuestions, result);

        verify(restTemplate).getForObject(anyString(), eq(OpenTriviaResponseDto.class));
        verify(mapper).toTriviaQuestions(response.results());
    }

    @Test
    void fetchQuestions_shouldReturnEmptyList_whenResponseIsNull() {

        when(restTemplate.getForObject(anyString(), eq(OpenTriviaResponseDto.class)))
                .thenReturn(null);

        var result = client.fetchQuestions();

        assertTrue(result.isEmpty());

        verify(mapper, never()).toTriviaQuestions(any());
    }

    @Test
    void fetchQuestions_shouldReturnEmptyList_whenResultsIsNull() {

        var response = new OpenTriviaResponseDto(0, null);

        when(restTemplate.getForObject(anyString(), eq(OpenTriviaResponseDto.class)))
                .thenReturn(response);

        var result = client.fetchQuestions();

        assertTrue(result.isEmpty());

        verify(mapper, never()).toTriviaQuestions(any());
    }

    @Test
    void fetchQuestions_shouldReturnEmptyList_whenResultsEmpty() {

        var response = new OpenTriviaResponseDto(0, List.of());

        when(restTemplate.getForObject(anyString(), eq(OpenTriviaResponseDto.class)))
                .thenReturn(response);

        var result = client.fetchQuestions();

        assertTrue(result.isEmpty());

        verify(mapper, never()).toTriviaQuestions(any());
    }

    private OpenTriviaResponseDto loadResponse(String file) throws Exception {
        String json = new String(
                getClass().getClassLoader()
                        .getResourceAsStream(file)
                        .readAllBytes()
        );

        return objectMapper.readValue(json, OpenTriviaResponseDto.class);
    }

    private TriviaQuestion createQuestion() {
        var question = new TriviaQuestion();
        question.setId("1");
        question.setQuestion("Q");
        question.setCorrectAnswer("A");
        return question;
    }
}