package de.mi.ur.HoffentlichNurVoruebergehend;

import java.util.ArrayList;

/**
 * Created by maxiwindl on 12.08.16.
 */
public class MultipleChoiceC extends QuestionC {
    private String question;
    private String rightAnswer;

    private int difficulty;

    // difficulty wird derzeit noch nicht genutzt... :(
    public MultipleChoiceC(int numeral1Base, int numeral2Base, int maxDigits, int difficulty) {
        super(numeral1Base, numeral2Base, maxDigits);
        this.difficulty = difficulty;

        this.question = generateNumWithMaxDigits(numeral1Base, maxDigits);
        this.rightAnswer = convertFromNumeralToNumeral(question, numeral1Base, numeral2Base);
    }

    public String getQuestion() {
        return question;
    }

    public String generateWrongAnswer() {
        String wrongAnswer = generateWrongAnswers(getNumeral2Base(), getMaxDigits());
        if (!wrongAnswer.equals(rightAnswer)) {
            return wrongAnswer;
        }
        return generateWrongAnswer();

    }

    public ArrayList<String> generatePossAnswers() {
        ArrayList<String> possAnswers = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String possAnswer = generateNumWithMaxDigits(getNumeral1Base(), getMaxDigits());
            if (!possAnswers.contains(possAnswer)) {
                possAnswers.add(possAnswer);
            }
        }
        possAnswers.add(rightAnswer);
        return possAnswers;
    }

    public String getQuestionNumber() {
        return question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public boolean isCorrectAnswer(String answer) {
        return answer.equals(rightAnswer);
    }

}
