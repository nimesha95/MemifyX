package com.example.nimesha.memifyx;

/**
 * Created by Oshan Wickramaratne on 2017-10-10.
 */

public class Question {
    String question;
    String questionID;
    public Question(String questionID, String question ) {
        this.question = question;
        this.questionID = questionID;
    }

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestionID() {
        return questionID;
    }
}
