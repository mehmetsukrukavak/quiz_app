package com.mehmetsukrukavak.quizservice.service;


import com.mehmetsukrukavak.quizservice.feign.QuizInterface;
import com.mehmetsukrukavak.quizservice.model.QuestionWrapper;
import com.mehmetsukrukavak.quizservice.model.Quiz;
import com.mehmetsukrukavak.quizservice.model.Response;
import com.mehmetsukrukavak.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questionIds = quizInterface.generateQuestion(category,numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds(questionIds);

        quizRepository.save(quiz);

        return new ResponseEntity<>("Quiz created",HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizRepository.findById(id).get();

        List<Integer> questionIds = quiz.getQuestionsIds();

        ResponseEntity<List<QuestionWrapper>> questions =  quizInterface.getQuestionsFromId(questionIds);

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
            return quizInterface.getScore(responses);
    }

    public ResponseEntity<String> createQuizWithoutCategory(int numQ, String title) {
        List<Integer> questionIds = quizInterface.generateQuestionWithoutCategory(numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds(questionIds);

        quizRepository.save(quiz);

        return new ResponseEntity<>("Quiz created",HttpStatus.CREATED);

    }
}
