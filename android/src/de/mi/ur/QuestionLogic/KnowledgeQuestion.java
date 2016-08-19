package de.mi.ur.QuestionLogic;

/**
 * Created by Anna-Marie on 19.08.2016.
 */
public class KnowledgeQuestion extends Question{
    String question;
    String rightAnswer;

    public KnowledgeQuestion(String question, String rightAnswer){
        super(-1,-1,-1);
        this.question = question;
        this.rightAnswer = rightAnswer;
    }

    @Override
    public boolean isCorrectAnswer(String answer){
        return answer.equals(rightAnswer);
    }

    @Override
    public String getQuestionString(){
        return question;
    }

    public String getRightAnswerString(){
        return rightAnswer;
    }
}
