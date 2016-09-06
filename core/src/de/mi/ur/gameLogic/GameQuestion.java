package de.mi.ur.gameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.ConstantsGame;

/**
 * Created by Anna-Marie on 30.08.2016.
 */
public class GameQuestion {
    private Score score;


    private String toSolve;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String rightAnswer;

    private String possAnswer1;
    private String possAnswer2;
    private String possAnswer3;
    private String possAnswer4;

    private String[] question;
    private ArrayList<String> answers;
    private ArrayList<Integer> used;
    private ArrayList<Integer> remaining;

    private ArrayList<String> possAnswers;

    private BitmapFont toSolveBitmap;
    private BitmapFont rightAnswerBitmap;
    private BitmapFont wrongAnswer1Bitmap;
    private BitmapFont wrongAnswer2Bitmap;


    private BitmapFont possAnswer1Bitmap;
    private BitmapFont possAnswer2Bitmap;
    private BitmapFont possAnswer3Bitmap;
    private BitmapFont possAnswer4Bitmap;


    private MultipleChoiceListener multipleChoiceGenerator;
    private int numeral1Base;
    private int numeral2Base = 10;
    private int maxDigits;

    private Random randomGen;


    //evtl Enum übergeben ob Hex oder Binär... Random-Gen...
    public GameQuestion(MultipleChoiceListener multipleChoiceGenerator) {
        randomGen = new Random();


        this.multipleChoiceGenerator = multipleChoiceGenerator;

        //binär oder Hex-Abfrage
        if (true) {
            numeral1Base = 2;
            maxDigits = 6;
        } else {
            numeral1Base = 16;
            maxDigits = 2;
        }


        score = new Score();
        toSolve = "";

        /*
        wrongAnswer1 = "";
        rightAnswer = "";
        wrongAnswer2 = "";
*/
        possAnswer1 = "";
        possAnswer2 = "";
        possAnswer3 = "";
        possAnswer4 = "";


        toSolveBitmap = new BitmapFont(Gdx.files.internal("good_times4question.fnt"));
        toSolveBitmap.setUseIntegerPositions(false);
/*

        rightAnswerBitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        rightAnswerBitmap.setUseIntegerPositions(false);

        wrongAnswer1Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        wrongAnswer1Bitmap.setUseIntegerPositions(false);

        wrongAnswer2Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        wrongAnswer2Bitmap.setUseIntegerPositions(false);


        */

        possAnswer1Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        possAnswer1Bitmap.setUseIntegerPositions(false);

        possAnswer2Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        possAnswer2Bitmap.setUseIntegerPositions(false);

        possAnswer3Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        possAnswer3Bitmap.setUseIntegerPositions(false);

        possAnswer4Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        possAnswer4Bitmap.setUseIntegerPositions(false);
    }


/*
    public ArrayList<String> generatePossAnswers() {
        ArrayList<String> possAnswers = new ArrayList<String>();


        for (int i = 0; i < 2; i++) {
            String possAnswer = generateWrongAnswer();
            if (!possAnswers.contains(possAnswer)) {
                possAnswers.add(possAnswer);
            }
        }
        rightAnswer = question[ConstantsGame.RIGHT_ANSWER_POS];
        System.out.println("richtige Lösung: " + rightAnswer);
        possAnswers.add(rightAnswer);

        for (String answers : possAnswers) {
            System.out.println("antwort: " + answers);
        }

        return possAnswers;
    }
    */

    public ArrayList<String> generatePossAnswers(){
        ArrayList<String> possAnswers = new ArrayList<String>();
        for(int i = ConstantsGame.POSS_ANSWER1_POS; i<=ConstantsGame.POSS_ANSWER4_POS; i++){
            possAnswers.add(question[i]);
        }

        return possAnswers;
    }


    public void updateQuestions(OrthographicCamera cam) {
        if (score.getCurrentScore() % 20 == 0) {
            question = multipleChoiceGenerator.getQuestionInfos(2, 10, 6, 0);
            toSolve = question[ConstantsGame.QUESTION_POS] + " ?";
            //answers = generatePossAnswers();
            possAnswers = generatePossAnswers();
            possAnswer1 = possAnswers.get(0);
            possAnswer2 = possAnswers.get(1);
            possAnswer3 = possAnswers.get(2);
            possAnswer4 = possAnswers.get(3);


            for (String answer : possAnswers) {
                System.out.println("Antworten 2: " + answer);
            }
            /*for (String answer : answers) {
                System.out.println("Antworten 2: " + answer);
            }
            */
            remaining = new ArrayList<Integer>();

            /*

            int pos1 = randomGen.nextInt(answers.size());
            System.out.println(" 1!!!!: " + pos1);
            remaining.add(0);
            remaining.add(1);
            remaining.add(2);
            if (remaining.contains(pos1)) {
                rightAnswer = answers.get(pos1);

                remaining.remove(pos1);
            }


            generateWrongAnswers();
*/
        }

    }

    private void generateWrongAnswers() {
        int pos2 = randomGen.nextInt(remaining.size());
        for (Integer nochÜbrig : remaining) {

        }

        if (remaining.contains(pos2)) {
            wrongAnswer1 = answers.get(pos2);
            System.out.println("2!!!!:" + pos2);
            remaining.remove(pos2);
        }
        int pos3 = randomGen.nextInt(remaining.size());
        System.out.println(" 3!!!!:" + pos3);
        if (remaining.contains(pos3)) {
            wrongAnswer2 = answers.get(pos3);
        }


    }


    public String generateWrongAnswer() {
        int randomLimit = (int) Math.pow(numeral1Base, maxDigits);
        String wrongAnswer = Integer.toString(randomGen.nextInt(randomLimit));
        ;
        if (!wrongAnswer.equals(rightAnswer)) {
            return wrongAnswer;
        }
        return generateWrongAnswer();

    }


    public void drawTasks(SpriteBatch batch, OrthographicCamera cam) {

        toSolveBitmap.draw(batch, toSolve, cam.position.x - 110, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        /*
        wrongAnswer1Bitmap.draw(batch, "1:" + wrongAnswer1, cam.position.x - 30, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        wrongAnswer1Bitmap.setColor(Color.BLACK);
        wrongAnswer2Bitmap.draw(batch, "2:" + wrongAnswer2, cam.position.x + 20, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        wrongAnswer2Bitmap.setColor(Color.BLACK);
        rightAnswerBitmap.draw(batch, "3:" + rightAnswer, cam.position.x + 70, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        rightAnswerBitmap.setColor(Color.BLACK);
*/

        possAnswer1Bitmap.draw(batch, "1:" + possAnswer1, cam.position.x - 40, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        possAnswer1Bitmap.setColor(Color.BLACK);
        possAnswer2Bitmap.draw(batch, "2:" + possAnswer2, cam.position.x, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        possAnswer2Bitmap.setColor(Color.BLACK);
        possAnswer3Bitmap.draw(batch, "3:" + possAnswer3, cam.position.x + 40, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        possAnswer3Bitmap.setColor(Color.BLACK);
        possAnswer4Bitmap.draw(batch, "4:" + possAnswer4, cam.position.x + 80, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y );
        possAnswer4Bitmap.setColor(Color.BLACK);

    }
}
