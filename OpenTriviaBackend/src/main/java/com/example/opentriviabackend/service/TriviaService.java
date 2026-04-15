package com.example.opentriviabackend.service;

import com.example.opentriviabackend.client.OpenTriviaClient;
import com.example.opentriviabackend.dto.answer.AnswerRequestDto;
import com.example.opentriviabackend.dto.answer.AnswerResultDto;
import com.example.opentriviabackend.model.TriviaQuestion;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TriviaService {

    private final OpenTriviaClient client;
    private final Map<String, TriviaQuestion> questionStore = new ConcurrentHashMap<>();

    public TriviaService(OpenTriviaClient client) {
        this.client = client;
    }

    @Cacheable(value = "questionsCache")
    public List<TriviaQuestion> getQuestions() {

        var questions = client.fetchQuestions();
        rebuildQuestionStore(questions);

        return questions;
    }

    public List<AnswerResultDto> checkAnswers(List<AnswerRequestDto> givenAnswers)  {

        var answerResults = new ArrayList<AnswerResultDto>();

        for (AnswerRequestDto givenAnswer : givenAnswers) {
            var question = questionStore.get(givenAnswer.questionId());

            if (question == null) {
                throw new IllegalArgumentException("Question not found for id: " + givenAnswer.questionId());
            }

            boolean isCorrect = question.getCorrectAnswer()
                    .equals(givenAnswer.givenAnswer());

            answerResults.add(new AnswerResultDto(
                    question.getId(),
                    question.getQuestion(),
                    isCorrect,
                    question.getCorrectAnswer()
            ));
        }

        return answerResults;
    }

    private void rebuildQuestionStore(List<TriviaQuestion> questions) {

        questionStore.clear();

        for (TriviaQuestion question : questions) {
            questionStore.put(question.getId(), question);
        }
    }
}