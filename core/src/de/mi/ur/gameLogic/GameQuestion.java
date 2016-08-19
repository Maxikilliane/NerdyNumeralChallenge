package de.mi.ur.gameLogic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import de.mi.ur.ConstantsGame;
import de.mi.ur.HoffentlichNurVoruebergehend.MultipleChoiceC;


/**
 * Created by maxiwindl on 09.08.16.
 */
public class GameQuestion extends MultipleChoiceC {

    private Rectangle bounds;
    private Score score;
    private MultipleChoiceC questions;

    private String toSolve;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String rightAnswer;
    private ArrayList<String> possAnswers;

    private BitmapFont toSolveBitmap;
    private BitmapFont rightAnswerBitmap;
    private BitmapFont wrongAnswer1Bitmap;
    private BitmapFont wrongAnswer2Bitmap;

    public boolean isSolved = true;

    private Vector2 rightAnswerPos;

    public GameQuestion() {
        super(2, 10, 3, 0);
        score = new Score();
        toSolve = "";
        wrongAnswer1 = "";
        rightAnswer = "";
        wrongAnswer2 = "";
        toSolveBitmap = new BitmapFont();
        toSolveBitmap.setUseIntegerPositions(false);
        toSolveBitmap.getData().scale(1);

        rightAnswerBitmap = new BitmapFont();
        rightAnswerBitmap.setUseIntegerPositions(false);

        wrongAnswer1Bitmap = new BitmapFont();
        wrongAnswer1Bitmap.setUseIntegerPositions(false);

        wrongAnswer2Bitmap = new BitmapFont();
        wrongAnswer2Bitmap.setUseIntegerPositions(false);

        //rightAnswerPos = new Vector2(x,0);

        //bounds = new Rectangle(rightAnswerPos.x, rightAnswerPos.y, BitmapFont.getB)
    }


    public void updateQuestions() {
        if (score.getCurrentScore() % 20==0) {
            questions = new MultipleChoiceC(2, 10, 6, 0);
            toSolve = questions.getQuestion();
            possAnswers = questions.generatePossAnswers();
            rightAnswer = questions.getRightAnswer();


            generateWrongAnswers();

       }
    }

    private void generateWrongAnswers() {
        wrongAnswer1 = generateWrongAnswer();
        wrongAnswer2 = generateWrongAnswer();
        if (wrongAnswer1.equals(wrongAnswer2)) {
            wrongAnswer2 = generateWrongAnswer();
        }
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }

    public void drawTasks(SpriteBatch batch, OrthographicCamera cam) {

        toSolveBitmap.draw(batch, toSolve, cam.position.x + ConstantsGame.QUESTION_OFFSET_X, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        toSolveBitmap.setColor(Color.GRAY);
        wrongAnswer1Bitmap.draw(batch, wrongAnswer1, cam.position.x + ConstantsGame.QUESTION_ANSWER_OFFSET_X, cam.position.y + ConstantsGame.QUESTION_ANSWER_OFFSET_Y);
        wrongAnswer1Bitmap.setColor(Color.BLACK);
        wrongAnswer2Bitmap.draw(batch, wrongAnswer2, cam.position.x + ConstantsGame.QUESTION_ANSWER_OFFSET_X + 20, cam.position.y + ConstantsGame.QUESTION_ANSWER_OFFSET_Y);
        wrongAnswer2Bitmap.setColor(Color.BLACK);
        rightAnswerBitmap.draw(batch, rightAnswer, cam.position.x + ConstantsGame.QUESTION_ANSWER_OFFSET_X + 40, cam.position.y + ConstantsGame.QUESTION_ANSWER_OFFSET_Y);
        rightAnswerBitmap.setColor(Color.BLACK);




    }

}
