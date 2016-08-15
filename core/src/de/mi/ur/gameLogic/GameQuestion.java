package de.mi.ur.gameLogic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
    private BitmapFont toSolveBitmap;
    private BitmapFont rightAnswerBitmap;
    private BitmapFont wrongAnswer1Bitmap;
    private BitmapFont wrongAnswer2Bitmap;
    private Vector2 rightAnswerPos;

    public GameQuestion() {
        super(2, 10, 3, 0);
        score = new Score();
        toSolve = "";
        toSolveBitmap = new BitmapFont();
        toSolveBitmap.setUseIntegerPositions(false);
        toSolveBitmap.getData().scale(1);

        rightAnswerBitmap = new BitmapFont();
        wrongAnswer1Bitmap = new BitmapFont();
        wrongAnswer2Bitmap = new BitmapFont();
        rightAnswerPos = new Vector2();
    }


    public void updateQuestions() {
        if (score.getCurrentScore() % 20==0) {
            questions = new MultipleChoiceC(2, 10, 5, 0);
            toSolve = questions.getQuestion();
            questions.generatePossAnswers();
            questions.getRightAnswerNumber();

       }
    }

    public void generateTasks(SpriteBatch batch, OrthographicCamera cam) {

        toSolveBitmap.draw(batch, toSolve, cam.position.x + ConstantsGame.QUESTION_OFFSET_X, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        toSolveBitmap.setColor(Color.GRAY
        );


    }

}
