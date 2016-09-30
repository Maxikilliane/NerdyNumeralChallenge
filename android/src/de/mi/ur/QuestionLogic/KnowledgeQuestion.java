package de.mi.ur.QuestionLogic;

import android.content.res.Resources;

/**
 * Created by Anna-Marie on 19.08.2016.
 *
 * Is a basis, so that they can be saved together with FreeTextQuestions in a single Instance Variable
 */
public class KnowledgeQuestion extends Question {
    String question;
    String rightAnswer;

    public KnowledgeQuestion(String question, String rightAnswer) {
        super(-1, -1, -1);
        this.question = question;
        this.rightAnswer = rightAnswer;
    }

    @Override
    public boolean isCorrectAnswer(String answer) {
        return answer.equals(rightAnswer);
    }

    @Override
    public String getQuestionString(Resources resources) {
        return question;
    }

    public String getRightAnswerString() {
        return rightAnswer;
    }
}
