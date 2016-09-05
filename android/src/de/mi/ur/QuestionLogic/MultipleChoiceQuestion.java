package de.mi.ur.QuestionLogic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Anna-Marie on 02.08.2016.
 */
public class MultipleChoiceQuestion extends Question {
    private String question;
    private String rightAnswer;
    private int difficulty;

    // difficulty wird derzeit noch nicht genutzt... :(
    public MultipleChoiceQuestion(int numeral1Base, int numeral2Base, int maxDigits) {
        super(numeral1Base, numeral2Base, maxDigits);
        this.difficulty = difficulty;

        this.question = generateNumWithMaxDigits(numeral1Base, maxDigits);
        this.rightAnswer = convertFromNumeralToNumeral(question, numeral1Base, numeral2Base);
    }

    public String[] generatePossAnswers() {
        ArrayList<String> possAnswers = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String possAnswer = generateNumBelowMax(getNumeral2Base(), (int) Math.pow(getNumeral1Base(), getMaxDigits()));
            while (possAnswers.contains(possAnswer) || possAnswer.equals(rightAnswer)) {
                possAnswer = generateNumBelowMax(getNumeral2Base(), (int) Math.pow(getNumeral1Base(), getMaxDigits()));
            }
            if (!possAnswers.contains(possAnswer)) {
                possAnswers.add(possAnswer);
            }
        }
            possAnswers.add(rightAnswer);
            Collections.shuffle(possAnswers);
            return possAnswers.toArray(new String[4]);

    }

    public String getQuestionNumber(){
        return question;
    }

    public String getRightAnswerString(){
        return rightAnswer;
    }

    @Override
    public boolean isCorrectAnswer(String answer){
        return answer.equals(rightAnswer);
    }

}
