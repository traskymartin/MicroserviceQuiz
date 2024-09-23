package com.trasky.quiz.service;


import com.trasky.quiz.feign.QuizInterface;
import com.trasky.quiz.model.QuestionWapper;
import com.trasky.quiz.model.Quiz;
import com.trasky.quiz.model.Response;
import com.trasky.quiz.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizRepo quizrepo;

    @Autowired
    QuizInterface quizInterface;
    
    public ResponseEntity<String> createQuiz(String category, int noQ, String title) {

       List<Integer> questions = quizInterface.getQuestionForQuiz(category,noQ).getBody();

        try {
            Quiz quiz=new Quiz();
            quiz.setTitle(title);
            quiz.setQuestion(questions);

            quizrepo.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("not succed,",HttpStatus.BAD_REQUEST)   ;
    }

    public ResponseEntity<List<QuestionWapper>> getQuizQuestion(int id) {
        try {
          Quiz quiz=quizrepo.findById(id).get();
          List<Integer> questionIds = quiz.getQuestion();

          ResponseEntity<List<QuestionWapper>> questions =
                  quizInterface.getQuestionFromIf(questionIds);
        return questions;

        } catch (Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        try {
            ResponseEntity<Integer> right= quizInterface.getScore(responses);
            return right;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>(-1,HttpStatus.BAD_REQUEST);
    }
}
