package de.mi.ur.QuestionLogic;

import android.content.res.Resources;

/**
 * Created by Anna-Marie on 02.08.2016.
 */
public class TutorialQuestion {
    private int tutorialTypeConstant;
    private int questionNumber;
    private Question question;

    private Question[][] questions = {
            {new KnowledgeQuestion("Welchen Wert hat die Ziffer 4 in der Zahl 9482?", "400"),
                    new KnowledgeQuestion("Welche Zahl ist größer: 001 oder 100?", "100"),
                    new KnowledgeQuestion("Wie viele Ziffern hat das Trinärsystem (zur Basis 3)?", "3"),
                    new KnowledgeQuestion("Was sind die Ziffern des Duodezimalsystems (Basis 12)? Mit 0 beginnen, durch , und Leerzeichen trennen!", "0, 1, 2, 3, 4, 5, 6, 7, 8, 9, A, B"),
                    new KnowledgeQuestion("Welche Basis hat das Quaternalsystem?", "4")},
            {new KnowledgeQuestion("Welchem Wert entspricht die 1 an der 4<sup><small>2</small></sup>-Stelle?", "16"),
                    new FreeTextQuestion(2, 10, 3),
                    new FreeTextQuestion(5, 10, 3)},
            {new KnowledgeQuestion("Was ist der größte Stellenwert aus dem Trinärsystem, der in 30 passt?", "27"),
                    new FreeTextQuestion(10, 2, 2),
                    new FreeTextQuestion(10, 2, 2),
                    new FreeTextQuestion(10, 4, 2),
                    new KnowledgeQuestion("Für welche Dezimalzahl steht 'D'?", "13")},
            {new KnowledgeQuestion("Was ist die Basis des nächsthöheren Zahlensystem mit einer Basis von 2<sup><small>n</small></sup>?", "32"),
                    new FreeTextQuestion(8, 2, 3),
                    new KnowledgeQuestion("Kann zwischen dem 4er und dem 16er System direkt umgerechnet werden? Ja oder Nein eingeben!", "Ja"),
                    new FreeTextQuestion(8, 10, 5),
                    new FreeTextQuestion(5, 3, 3)
            }
    };

    public TutorialQuestion(int tutorialTypeConstant, int questionNumber) {
        this.tutorialTypeConstant = tutorialTypeConstant;
        this.questionNumber = questionNumber;
        this.question = questions[tutorialTypeConstant][questionNumber];
    }

    public String getQuestion(Resources resources){
        return this.question.getQuestionString(resources);
    }

    public String getRightAnswer(){
        return this.question.getRightAnswerString();
    }

    public boolean isCorrectAnswer(String answer){
        return questions[tutorialTypeConstant][questionNumber].isCorrectAnswer(answer);
    }


}

