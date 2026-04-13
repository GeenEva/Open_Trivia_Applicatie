package com.example.opentriviabackend.mapper;

import com.example.opentriviabackend.dto.response.OpenTriviaResponseDto;
import com.example.opentriviabackend.model.TriviaQuestion;
import org.apache.commons.text.StringEscapeUtils;

import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TriviaMapper {

    List<TriviaQuestion> toTriviaQuestions(List<OpenTriviaResponseDto.QuestionDto> dtoList);

    @Mapping(target = "id", expression = "java(generateId())")
    TriviaQuestion toTriviaQuestion(OpenTriviaResponseDto.QuestionDto dto);

    default String generateId() {
        return UUID.randomUUID().toString();
    }

    default String decode(String input) {
        return input == null ? null : StringEscapeUtils.unescapeHtml4(input);
    }

    @AfterMapping
    default void enrich(@MappingTarget TriviaQuestion target,
                        OpenTriviaResponseDto.QuestionDto dto) {

        target.setQuestion(decode(dto.question()));
        target.setCorrectAnswer(decode(dto.correctAnswer()));

        List<String> allAnswers = new ArrayList<>();

        dto.incorrectAnswers()
                .forEach(answer -> allAnswers.add(decode(answer)));

        allAnswers.add(decode(dto.correctAnswer()));
        Collections.shuffle(allAnswers);

        target.setAllAnswers(allAnswers);
    }
}