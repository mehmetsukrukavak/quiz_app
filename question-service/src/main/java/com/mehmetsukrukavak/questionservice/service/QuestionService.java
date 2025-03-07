package com.mehmetsukrukavak.questionservice.service;

import com.mehmetsukrukavak.questionservice.model.Question;
import com.mehmetsukrukavak.questionservice.model.QuestionWrapper;
import com.mehmetsukrukavak.questionservice.model.Response;
import com.mehmetsukrukavak.questionservice.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {


    @Autowired
    QuestionRepository questionRepository;
    public ResponseEntity<List<Question>> getAllQuestions() {

        try {
            return new ResponseEntity<>(questionRepository.findAll(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findAllByCategory(category),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionRepository.save(question);
            return new ResponseEntity<>("Successfully Added Id: " + question.getId().toString(),HttpStatus.CREATED);
        }catch (Exception e){e.printStackTrace();}

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        try {
            questionRepository.save(question);
            return new ResponseEntity<>("Successfully Updated Id: " + question.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            questionRepository.deleteById(id);
            return new ResponseEntity<>("Successfully Deleted Id: " + id, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<Question> getQuestionById(Integer id) {

        try {
            questionRepository.deleteById(id);
            return new ResponseEntity<>(questionRepository.findById(id).orElse(null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


    public ResponseEntity<List<Integer>> getQuestionForQuiz(String category, Integer numQuestion) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(category, numQuestion);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionForQuizWithoutCategory(Integer numQuestion) {
        List<Integer> questions = questionRepository.findRandomQuestions(numQuestion);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = questionRepository.findAllById(questionIds);

        for (Question q : questions) {
            wrappers.add(new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4()));
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right = 0;

        for (Response r : responses) {
            Question question = questionRepository.findById(r.getId()).get();
            if (r.getResponse().equals(question.getRightAnswer()))
                right++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
