package com.mehmetsukrukavak.quizservice.feign;

import com.mehmetsukrukavak.quizservice.model.QuestionWrapper;
import com.mehmetsukrukavak.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> generateQuestion(@RequestParam String category, @RequestParam Integer numQuestion);

    @GetMapping("question/generateWithoutCategory")
    public ResponseEntity<List<Integer>> generateQuestionWithoutCategory(@RequestParam Integer numQuestion);

    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
