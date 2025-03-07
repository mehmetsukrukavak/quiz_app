package com.mehmetsukrukavak.quizservice.model;

import lombok.Data;

@Data

public class QuizDto {
    String categoryName;
    int numQuestions;
    String title;
}
