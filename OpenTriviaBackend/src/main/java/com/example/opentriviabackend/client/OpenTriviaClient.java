package com.example.opentriviabackend.client;

import com.example.opentriviabackend.dto.response.OpenTriviaResponseDto;
import com.example.opentriviabackend.mapper.TriviaMapper;
import com.example.opentriviabackend.model.TriviaQuestion;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OpenTriviaClient {

    private final RestTemplate restTemplate;
    private final TriviaMapper mapper;

    private static final String URL =
            "https://opentdb.com/api.php?amount=5&category=31&difficulty=easy&type=multiple";

    public OpenTriviaClient(RestTemplate restTemplate, TriviaMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public List<TriviaQuestion> fetchQuestions() {
        var response =
                restTemplate.getForObject(URL, OpenTriviaResponseDto.class);

        if (response == null || response.results() == null ||
                response.results().isEmpty()) {
            return List.of();
        }

        return mapper.toTriviaQuestions(response.results());
    }
}