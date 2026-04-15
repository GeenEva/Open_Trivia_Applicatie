package com.example.opentriviabackend.client;

import com.example.opentriviabackend.dto.clientResponse.OpenTriviaResponseDto;
import com.example.opentriviabackend.mapper.TriviaMapper;
import com.example.opentriviabackend.model.TriviaQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OpenTriviaClient {

    private static final Logger log = LoggerFactory.getLogger(OpenTriviaClient.class);

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

        if (response == null ) {
            log.warn("OpenTrivia API gaf geen response");
            return List.of();
        }

        if (response.responseCode() != 0) {
            log.warn("OpenTrivia API returned error code: {}", response.responseCode());
            throw new IllegalStateException("Trivia vragen konden niet goed worden opgehaald.");
        }

        if ( response.results() == null || response.results().isEmpty()) {
            log.warn("OpenTrivia API gaf geen vragen terug");
            return List.of();
        }

        return mapper.toTriviaQuestions(response.results());
    }
}