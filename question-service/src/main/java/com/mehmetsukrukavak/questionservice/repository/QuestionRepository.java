package com.mehmetsukrukavak.questionservice.repository;

import com.mehmetsukrukavak.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository  extends JpaRepository<Question, Integer> {
    List<Question> findAllByCategory(String category);

    @Query(value = "SELECT q.id FROM question q where q.category =:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);

    @Query(value = "SELECT q.id FROM question q ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
    List<Integer> findRandomQuestions(int numQ);
}
