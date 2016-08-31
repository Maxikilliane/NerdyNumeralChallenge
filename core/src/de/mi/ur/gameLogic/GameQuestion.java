package de.mi.ur.gameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

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


    private BitmapFont toSolveBitmap;
    private BitmapFont rightAnswerBitmap;
    private BitmapFont wrongAnswer1Bitmap;
    private BitmapFont wrongAnswer2Bitmap;

    private GlyphLayout layout;

    private Label label;


    private Vector2 rightAnswerPos;

    public GameQuestion() {
        super(2, 10, 3, 0);

        score = new Score();
        toSolve = "";
        wrongAnswer1 = "";
        rightAnswer = "";
        wrongAnswer2 = "";
        toSolveBitmap = new BitmapFont(Gdx.files.internal("goodTimes4Question.fnt"));
        toSolveBitmap.setUseIntegerPositions(false);


        rightAnswerBitmap = new BitmapFont(Gdx.files.internal("goodTimesNew.fnt"));
        rightAnswerBitmap.setUseIntegerPositions(false);

        wrongAnswer1Bitmap = new BitmapFont(Gdx.files.internal("goodTimesNew.fnt"));
        wrongAnswer1Bitmap.setUseIntegerPositions(false);

        wrongAnswer2Bitmap = new BitmapFont(Gdx.files.internal("goodTimesNew.fnt"));
        wrongAnswer2Bitmap.setUseIntegerPositions(false);

        //label = new Label(wrongAnswer1,)


        rightAnswerPos = new Vector2(150, 0);
        layout = new GlyphLayout();
        layout.setText(rightAnswerBitmap, rightAnswer);
        bounds = new Rectangle(rightAnswerPos.x, rightAnswerPos.y, layout.width, layout.height);
    }

    public float getRightAnswerWidth() {
        return layout.width;
    }


    public void updateQuestions(OrthographicCamera cam) {
        if (score.getCurrentScore() % 20==0) {
            questions = new MultipleChoiceC(2, 10, 6, 0);
            toSolve = questions.getQuestion();

            rightAnswer = questions.getRightAnswer();


            generateWrongAnswers();

       }
        if (cam.position.x - (cam.viewportWidth / 2) > rightAnswerPos.x + getRightAnswerWidth()) {
            reposition(rightAnswerPos.x + 800);
        }
    }

    private void generateWrongAnswers() {
        wrongAnswer1 = generateWrongAnswer();
        wrongAnswer2 = generateWrongAnswer();
        if (wrongAnswer1.equals(wrongAnswer2)) {
            wrongAnswer2 = generateWrongAnswer();
        }
    }

    public void reposition(float x) {
        rightAnswerPos.set(x, 0);
        bounds.setPosition(rightAnswerPos.x, rightAnswerPos.y);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }

    public void drawTasks(SpriteBatch batch, OrthographicCamera cam) {

        toSolveBitmap.draw(batch, toSolve, cam.position.x + ConstantsGame.QUESTION_OFFSET_X, cam.position.y + ConstantsGame.QUESTION_OFFSET_Y);
        wrongAnswer1Bitmap.draw(batch, wrongAnswer1, cam.position.x + ConstantsGame.QUESTION_ANSWER_OFFSET_X, cam.position.y + ConstantsGame.QUESTION_ANSWER_OFFSET_Y);
        wrongAnswer1Bitmap.setColor(Color.BLACK);
        wrongAnswer2Bitmap.draw(batch, wrongAnswer2, cam.position.x + ConstantsGame.QUESTION_ANSWER_OFFSET_X + 40, cam.position.y + ConstantsGame.QUESTION_ANSWER_OFFSET_Y);
        wrongAnswer2Bitmap.setColor(Color.BLACK);
        rightAnswerBitmap.draw(batch, rightAnswer, cam.position.x + ConstantsGame.QUESTION_ANSWER_OFFSET_X + 80, cam.position.y + ConstantsGame.QUESTION_ANSWER_OFFSET_Y);
        rightAnswerBitmap.setColor(Color.BLACK);




    }

}
