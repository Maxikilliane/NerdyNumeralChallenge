package de.mi.ur.QuestionLogic;

import android.content.res.Resources;

/**
 * Created by Anna-Marie on 02.08.2016.
 *
 */
public class FreeTextQuestion extends Question {
    private String questionNumber;
    private String rightAnswer;


    public FreeTextQuestion(int numeral1Base, int numeral2Base, int maxDigits) {
        super(numeral1Base, numeral2Base, maxDigits);
        this.questionNumber = NumeralConverter.generateNumWithMaxDigits(numeral1Base, maxDigits);

        this.rightAnswer = NumeralConverter.convertFromNumeralToNumeral(questionNumber, numeral1Base, numeral2Base);

    }

    /*
     * Gets the Number in the 1st numeral system, which has to be converted.
     */
    public String getQuestion() {
        return questionNumber;
    }

    @Override
    public String getQuestionString(){
        return "Rechnen Sie die im " + getNumeral1Base() + "er-System gegebene Zahl in das " + getNumeral2Base() + "er-System um: " + questionNumber;
    }

    /*
     * gets a String with the right Answer
     */
    @Override
    public String getRightAnswerString(){
        return rightAnswer;
    }

    /*
     * Checks if the given String is the same as the right Answer.
     * Because leading zeros do not change the value of a number, they are trimmed away.
     */
    @Override
    public boolean isCorrectAnswer(String answer){
        return trimLeadingZeros(answer).equals(rightAnswer);
    }

    /*
     * checks if there are leading zeros at the beginning of the given String and trims them away.
     */
    private String trimLeadingZeros(String string) {
        String trimmed = string.trim();
        for (int i = 0; i < string.length() - 1; i++) {
            if (string.charAt(i) == '0') {
                trimmed = trimmed.substring(1);
            } else {
                break;
            }
        }
        return trimmed;
    }
}
