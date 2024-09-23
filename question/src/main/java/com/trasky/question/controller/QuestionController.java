package com.trasky.question.controller;


import java.util.List;

import com.trasky.question.model.QuestionWapper;
import com.trasky.question.model.Response;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trasky.question.model.Question;
import com.trasky.question.service.QuestionService;


@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {
    @Autowired
    private final QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getallQuestion(){
        return questionService.getallQuestion();
    }
    
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("category") String category){
        return questionService.getQuestionsByCategory(category);

    }
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }


    //generate
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz
            (@RequestParam String categoryName,@RequestParam Integer numQuestions){
        return questionService.getQuestionForQuiz(categoryName,numQuestions);
    }

    //getQuestions(questionid)
    @PostMapping("getQuestions")
    public  ResponseEntity<List<QuestionWapper>> getQuestionFromIf(@RequestBody List<Integer> questionIds){
        return questionService.getFromId(questionIds);
    }

    //getScource

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
