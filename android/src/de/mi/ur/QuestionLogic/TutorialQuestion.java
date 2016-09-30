package de.mi.ur.QuestionLogic;

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

    public String getQuestion(){
        return this.question.getQuestionString();
    }

    public String getRightAnswer(){
        return this.question.getRightAnswerString();
    }

    public boolean isCorrectAnswer(String answer){
        return questions[tutorialTypeConstant][questionNumber].isCorrectAnswer(answer);
    }


}
    /*
    private String question;
    private String rightAnswer;

    private FreeTextQuestion question1 = new FreeTextQuestion(2, 10, 3);
    private FreeTextQuestion question2 = new FreeTextQuestion(5, 10, 3);
    private FreeTextQuestion question3 = new FreeTextQuestion(10, 2, 2);// egt sollte 10 kleiner 32 sein
    private FreeTextQuestion question4 = new FreeTextQuestion(10, 2, 2);// egt sollte 10 kleiner 64 sein
    private FreeTextQuestion question5 = new FreeTextQuestion(10, 4, 2); // egt Ergebnis kleiner 6-Stellen
    private FreeTextQuestion question6 = new FreeTextQuestion(8, 2, 3);
    private FreeTextQuestion question7 = new FreeTextQuestion(8, 10, 5);
    private FreeTextQuestion question8 = new FreeTextQuestion(5, 3, 3);

    // 2. Tutorial, 1. Frage noch anpassen.
    private String[][] questions = {
            {"Welchen Wert hat die Ziffer 4 in der Zahl 9482?", "Welche Zahl ist größer: 001 oder 100?", "Wie viele Ziffern hat das Trinärsystem (zur Basis 3)?", "Was sind die Ziffern des Duodezimalsystems (Basis 12)?", "Welche Basis hat das Quaternalsystem?" },
            {"Welchem Wert entspricht die 1 an der 4<sup><small>2</small></sup>-Stelle?", question1.getQuestionString(), question2.getQuestionString()},
            {"Was ist der größte Stellenwert aus dem Trinärsystem, der in 30 passt?", question3.getQuestionString(),question4.getQuestionString(), question5.getQuestionString(), "Für welche Dezimalzahl steht 'D'?"  },
            {"Was ist die Basis des nächsthöheren Zahlensystem mit einer Basis von 2<sup><small>n</small></sup>", question6.getQuestionString(), "Kann zwischen dem 4er und dem 16er System direkt umgerechnet werden?", question7.getQuestionString(), question8.getQuestionString() }
    };

    private String[][] answers = {
            {"400", "100", "3", "0, 1, 2, 3, 4 , 5, 6, 7, 8, 9, A, B", "4"},
            {"16", question1.getRightAnswerString(), question2.getRightAnswerString()},
            {"27", question3.getRightAnswerString(), question4.getRightAnswerString(), question5.getRightAnswerString(), "13" },
            {"32", question6.getRightAnswerString(), "ja", question7.getRightAnswerString(), question8.getRightAnswerString() }
    };


    public TutorialQuestion(int tutorialTypeConstant, int questionNumber){
        super(-1, -1, -1);
        this.question = questions[tutorialTypeConstant][questionNumber];
        this.rightAnswer = answers[tutorialTypeConstant][questionNumber];
    }

    public String getQuestion(){
        return this.question;
    }

    public String getRightAnswer(){
        return this.rightAnswer;
    }

    public boolean isCorrectAnswer(String answer){
        return answer.equals(rightAnswer);
    }
    }
*/
