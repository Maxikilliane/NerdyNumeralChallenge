package de.mi.ur.gameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.ConstantsGame;
import de.mi.ur.sprites.AnswerPhone;
import de.mi.ur.states.PlayState;

public class GameQuestion {
    private Score score;


    private String toSolve;

    private static String possAnswer1;
    private static String possAnswer2;
    private static String possAnswer3;
    private static String possAnswer4;
    public static boolean answerGenerated = false;

    private static String[] question;

    private static ArrayList<String> possAnswers;

    private BitmapFont toSolveBitmap;

    private BitmapFont possAnswer1Bitmap;
    private BitmapFont possAnswer2Bitmap;
    private BitmapFont possAnswer3Bitmap;
    private BitmapFont possAnswer4Bitmap;


    private MultipleChoiceListener multipleChoiceGenerator;
    private int numeral1Base;
    private int numeral2Base = 10;
    private int maxDigits;

    private boolean counted;

    private Random random;
    private boolean isBinary;


    //evtl Enum übergeben ob Hex oder Binär... Random-Gen...
    public GameQuestion(MultipleChoiceListener multipleChoiceGenerator) {
        random = new Random();

        counted = false;
        this.multipleChoiceGenerator = multipleChoiceGenerator;



        score = new Score();
        toSolve = "";

        possAnswer1 = "";
        possAnswer2 = "";
        possAnswer3 = "";
        possAnswer4 = "";


        toSolveBitmap = new BitmapFont(Gdx.files.internal("good_times4question.fnt"));
        toSolveBitmap.setUseIntegerPositions(false);

        possAnswer1Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        possAnswer1Bitmap.setUseIntegerPositions(false);

        possAnswer2Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        possAnswer2Bitmap.setUseIntegerPositions(false);

        possAnswer3Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        possAnswer3Bitmap.setUseIntegerPositions(false);

        possAnswer4Bitmap = new BitmapFont(Gdx.files.internal("cantarrell4question.fnt"));
        possAnswer4Bitmap.setUseIntegerPositions(false);
    }


    public ArrayList<String> generatePossAnswers() {
        ArrayList<String> possAnswers = new ArrayList<String>();
        for (int i = ConstantsGame.POSS_ANSWER1_POS; i <= ConstantsGame.POSS_ANSWER4_POS; i++) {
            possAnswers.add(question[i]);
        }

        return possAnswers;
    }

    public static int getRightAnswer() {
        String rightAnswer = question[ConstantsGame.RIGHT_ANSWER_POS];
        if (rightAnswer.equals(possAnswer1)) {
            return possAnswers.indexOf(possAnswer1) + 1;
        }
        if (rightAnswer.equals(possAnswer2)) {
            return possAnswers.indexOf(possAnswer2) + 1;
        }
        if (rightAnswer.equals(possAnswer3)) {
            return possAnswers.indexOf(possAnswer3) + 1;
        }
        if (rightAnswer.equals(possAnswer4)) {
            return possAnswers.indexOf(possAnswer4) + 1;
        }
        return 0;
    }

    private void binaryOrHex() {
        isBinary = random.nextBoolean();

        //binär oder Hex-Abfrage
        if (isBinary) {
            numeral1Base = 2;
            maxDigits = 6;
        } else {
            numeral1Base = 16;
            maxDigits = 2;
        }


    }

    public void updateQuestions() {
        binaryOrHex();

        if (PlayState.isQuestionPhase() && !isCounted()) {

            question = multipleChoiceGenerator.getQuestionInfos(numeral1Base, numeral2Base, maxDigits, 0);
            if (isBinary) {

                toSolve = question[ConstantsGame.QUESTION_POS] + " (2)";
            } else {
                toSolve = question[ConstantsGame.QUESTION_POS] + " (16)";
            }
            possAnswers = generatePossAnswers();
            possAnswer1 = possAnswers.get(0);
            possAnswer2 = possAnswers.get(1);
            possAnswer3 = possAnswers.get(2);
            possAnswer4 = possAnswers.get(3);

            answerGenerated = true;
            AnswerPhone.resetCounted();



            setCounted();


        }
    }


    public void drawTasks(SpriteBatch batch, OrthographicCamera cam) {

        toSolveBitmap.draw(batch, toSolve, cam.position.x - 110, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);


        possAnswer1Bitmap.draw(batch, "1:" + possAnswer1, cam.position.x - 40, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);

        possAnswer2Bitmap.draw(batch, "2:" + possAnswer2, cam.position.x, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);

        possAnswer3Bitmap.draw(batch, "3:" + possAnswer3, cam.position.x + 40, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);

        possAnswer4Bitmap.draw(batch, "4:" + possAnswer4, cam.position.x + 80, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);


    }

    public boolean isCounted() {
        return counted;
    }

    public void setCounted() {
        counted = true;
    }

    public void resetCounted() {
        counted = false;
    }
}
