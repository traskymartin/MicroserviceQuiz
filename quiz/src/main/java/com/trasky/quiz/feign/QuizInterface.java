package com.trasky.quiz.feign;

import com.trasky.quiz.model.QuestionWapper;
import com.trasky.quiz.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION")
public interface QuizInterface {
    //generate
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz
    (@RequestParam String categoryName, @RequestParam Integer numQuestions);

    //getQuestions(questionid)
    @PostMapping("question/getQuestions")
    public  ResponseEntity<List<QuestionWapper>> getQuestionFromIf(@RequestBody List<Integer> questionIds);

    //getScource

    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
