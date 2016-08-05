package de.mi.ur.QuestionLogic;

/**
 * Created by Anna-Marie on 02.08.2016.
 */
public class FreeTextQuestion extends Question {
    private String question;
    private String rightAnswer;

    public FreeTextQuestion(int numeral1Base, int numeral2Base, int maxDigits) {
        super(numeral1Base, numeral2Base, maxDigits);

        this.question = generateNumWithMaxDigits(numeral1Base, maxDigits);
        this.rightAnswer = convertFromNumeralToNumeral(question, numeral1Base, numeral2Base);

    }

    public String getQuestionString(){
        return question;
    }

    public String getRightAnswerString(){
        return rightAnswer;
    }

    public boolean isCorrectAnswer(String answer){
        return answer.equals(rightAnswer);
    }
}
