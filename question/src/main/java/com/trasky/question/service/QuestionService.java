package com.trasky.question.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.trasky.question.model.QuestionWapper;
import com.trasky.question.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.trasky.question.model.Question;
import com.trasky.question.repository.QuestionRepo;



@Service
public class QuestionService {
    @Autowired
    QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getallQuestion() {
        try{
            return new ResponseEntity<>( questionRepo.findAll(),HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
        return new ResponseEntity<>(questionRepo.findByCategory(category),HttpStatus.OK);
        }catch (Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<String> addQuestion(Question question) {
        try {
            
        questionRepo.save(question);
        return new ResponseEntity<>( "success",HttpStatus.OK);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>("not Succed",HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions=questionRepo.findRandomQuestionByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWapper>> getFromId(List<Integer> questionIds) {
        // Fetch all questions at once based on the list of IDs
        List<Question> questions = questionRepo.findAllById(questionIds);

        // Transform the Question objects into QuestionWapper objects using Stream API
        List<QuestionWapper> wappers = questions.stream().map(question -> {
            QuestionWapper wapper = new QuestionWapper();
            wapper.setId(question.getId());
            wapper.setQuestionTitle(question.getQuestionTitle());
            wapper.setOption1(question.getOption1());
            wapper.setOption2(question.getOption2());
            wapper.setOption3(question.getOption3());
            wapper.setOption4(question.getOption4());
            return wapper;
        }).collect(Collectors.toList());

        // Return the response entity with HttpStatus OK
        return new ResponseEntity<>(wappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right=0;
        for(Response response:responses){
            Question question = questionRepo.findById(response.getId()).get();
            if (response.getResponse().equals(question.getRightAnswer()))
                right++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
