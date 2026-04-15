package com.example.opentriviabackend.control;

import com.example.opentriviabackend.dto.answer.AnswerRequestDto;
import com.example.opentriviabackend.dto.answer.AnswerResultDto;
import com.example.opentriviabackend.model.TriviaQuestion;
import com.example.opentriviabackend.service.TriviaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opentrivia")
@CrossOrigin(origins = "http://localhost:4200")
public class OpenTriviaController {

    private final TriviaService triviaService;

    public OpenTriviaController(TriviaService openTriviaService) {
        this.triviaService = openTriviaService;
    }

    @GetMapping("/questions")
    public List<TriviaQuestion> getQuestions(){
        return triviaService.getQuestions();
    }

    @PostMapping("/checkAnswers")
    public List<AnswerResultDto> checkAnswers(@Valid @RequestBody List<AnswerRequestDto> givenAnswers) {
        return triviaService.checkAnswers(givenAnswers);
    }

}
