package com.trasky.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trasky.question.model.Question;



@Repository
public interface QuestionRepo  extends JpaRepository<Question,Integer>{

    List<Question> findByCategory(String category); 

    @Query(value = "SELECT q.id FROM question q Where q.category=:category ORDER BY RAND() LIMIT :noQ",nativeQuery = true)
    List<Integer> findRandomQuestionByCategory(@Param("category") String category,@Param("noQ") int noQ);
} 
