package com.example.opentriviabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class TriviaQuestion {

    private String id;
    private String question;
    @JsonIgnore
    private String correctAnswer;
    private List<String> allAnswers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getAllAnswers() {
        return allAnswers;
    }

    public void setAllAnswers(List<String> allAnswers) {
        this.allAnswers = allAnswers;
    }
}
