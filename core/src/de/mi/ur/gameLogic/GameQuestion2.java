package de.mi.ur.gameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.ConstantsGame;
import de.mi.ur.HoffentlichNurVoruebergehend.MultipleChoiceC;

/**
 * Created by Anna-Marie on 30.08.2016.
 */
public class GameQuestion2 {
    private Rectangle bounds;
    private Score score;


    private String toSolve;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String rightAnswer;


    private BitmapFont toSolveBitmap;
    private BitmapFont rightAnswerBitmap;
    private BitmapFont wrongAnswer1Bitmap;
    private BitmapFont wrongAnswer2Bitmap;

    private GlyphLayout layout;

    private MultipleChoiceListener multipleChoiceGenerator;
    private int numeral1Base;
    private int numeral2Base = 10;
    private int maxDigits;

    private Random randomGen;

    private Vector2 rightAnswerPos;

    //evtl Enum übergeben ob Hex oder Binär... Random-Gen...
    public GameQuestion2(MultipleChoiceListener multipleChoiceGenerator) {
        randomGen = new Random();

        this.multipleChoiceGenerator = multipleChoiceGenerator;

        //binär oder Hex-Abfrage
        if(true){
            numeral1Base = 2;
            maxDigits = 6;
        }else{
            numeral1Base = 16;
            maxDigits = 2;
        }


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
            String[] question = multipleChoiceGenerator.getQuestionInfos(2, 10, 6, 0);
            toSolve = question[ConstantsGame.QUESTION_POS];
            rightAnswer = question[ConstantsGame.RIGHT_ANSWER_POS];

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


    public String generateWrongAnswer() {
        int randomLimit = (int) Math.pow(numeral1Base, maxDigits);
        String wrongAnswer = Integer.toString(randomGen.nextInt(randomLimit));;
        if (!wrongAnswer.equals(rightAnswer)) {
            return wrongAnswer;
        }
        return generateWrongAnswer();

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
