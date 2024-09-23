package com.trasky.quiz.controller;



import com.trasky.quiz.model.QuestionWapper;
import com.trasky.quiz.model.Quiz;
import com.trasky.quiz.model.QuizDto;
import com.trasky.quiz.model.Response;
import com.trasky.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")

public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(
                quizDto.getCategoryName(),quizDto.getNumQuestions(),quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWapper>> getQuizQuestion(@PathVariable("id") int id){
       return quizService.getQuizQuestion(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable("id") Integer id,@RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);

    }
}
