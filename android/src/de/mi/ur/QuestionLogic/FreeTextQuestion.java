package de.mi.ur.QuestionLogic;

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

    public String getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public String getQuestionString(){
        return "Rechnen Sie die im " + getNumeral1Base() + "er-System gegebene Zahl in das " + getNumeral2Base() + "er-System um: " + questionNumber;
    }

    @Override
    public String getRightAnswerString(){
        return rightAnswer;
    }

    @Override
    public boolean isCorrectAnswer(String answer){
        return trimLeadingZeros(answer).equals(rightAnswer);
    }

    private String trimLeadingZeros(String string){
        String trimmed = string.trim();
        for(int i = 0; i<string.length()-1; i++){
            if(string.charAt(i)=='0'){
                trimmed = trimmed.substring(1);
            }else{
                break;
            }
        }
        return trimmed;
    }
}
