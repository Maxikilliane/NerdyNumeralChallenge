package de.mi.ur.QuestionLogic;

import android.content.Context;

/**
 * Created by Anna-Marie on 02.08.2016.
 */
public class FreeTextQuestion extends Question {
    private String questionNumber;
    private String rightAnswer;

    public FreeTextQuestion(int numeral1Base, int numeral2Base, int maxDigits) {
        super(numeral1Base, numeral2Base, maxDigits);

        this.questionNumber = generateNumWithMaxDigits(numeral1Base, maxDigits);

        this.rightAnswer = convertFromNumeralToNumeral(questionNumber, numeral1Base, numeral2Base);

    }

    public String getQuestionNumber(){
        return questionNumber;
    }

    public String getQuestionString(){
        return "Rechnen Sie die im "+getNumeral1Base()+"er-System gegebene Zahl in das "+getNumeral2Base()+"er-System um: "+questionNumber;
    }

    public String getRightAnswerString(){
        return rightAnswer;
    }

    public boolean isCorrectAnswer(String answer){
        return answer.equals(rightAnswer);
    }
}
