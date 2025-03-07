package com.mehmetsukrukavak.questionservice.controller;

import com.mehmetsukrukavak.questionservice.model.Question;
import com.mehmetsukrukavak.questionservice.model.QuestionWrapper;
import com.mehmetsukrukavak.questionservice.model.Response;
import com.mehmetsukrukavak.questionservice.service.QuestionService;
import org.bouncycastle.jce.ECPointUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {


        return questionService.getAllQuestions();
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id) {
        return questionService.getQuestionById(id);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
        return questionService.getQuestionByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
            return questionService.addQuestion(question);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("delete/{Id}")
    public ResponseEntity<String> delete(@PathVariable Integer Id) {
        return questionService.deleteQuestion(Id);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestion(@RequestParam String category, @RequestParam Integer numQuestion) {
        return questionService.getQuestionForQuiz(category, numQuestion);
    }

    @GetMapping("generateWithoutCategory")
    public ResponseEntity<List<Integer>> generateQuestionWithoutCategory(@RequestParam Integer numQuestion) {
        return questionService.getQuestionForQuizWithoutCategory(numQuestion);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }
}
