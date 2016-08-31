package de.mi.ur.gameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import de.mi.ur.ConstantsGame;
import de.mi.ur.states.PlayState;

/**
 * Created by maxiwindl on 07.08.16.
 */
public class Score {


    private String currentScore;
    private Texture heartFilled;
    public static Array<Texture> hearts;
    private long startTime;
    BitmapFont scoreFont;
    private Texture heartEmpty;
    public static int thisCounter = 0;
    private Label testLabel;

    public Score() {
        hearts = new Array<Texture>();
        currentScore = "Score: 0";
        scoreFont = new BitmapFont(Gdx.files.internal("goodTimesNew.fnt"));
        heartFilled = new Texture("heart_filled.png");
        heartEmpty = new Texture("heart_empty_empty.png");
        scoreFont.setUseIntegerPositions(false);


    }

    public void renderScore(SpriteBatch batch, OrthographicCamera cam) {
        for (int i = 0; i < 3; i++) {
            hearts.add(heartFilled);


            batch.draw(hearts.get(i), cam.position.x + ConstantsGame.SCORE_HEARTS_OFFSET_X + i * hearts.get(i).getWidth(), cam.position.y + ConstantsGame.SCORE_HEARTS_OFFSET_Y);
        }



        scoreFont.draw(batch, currentScore, cam.position.x + ConstantsGame.SCORE_OFFSET_X, cam.position.y + ConstantsGame.SCORE_OFFSET_Y);
        scoreFont.setColor(Color.BLACK);

    }

    public long getCurrentScore() {
        return getTimeElapsed();
    }

    public long startTimer() {
        startTime = TimeUtils.millis();
        return startTime;
    }

    private long getTimeElapsed() {
        return TimeUtils.timeSinceMillis(startTime) / 1000;
    }

    private void updateHeart() {
        if (PlayState.hasHit) {
            thisCounter++;
            if (thisCounter == 6) {
                hearts.set(0, heartEmpty);
            }
            if (thisCounter == 12) {
                hearts.set(1, heartEmpty);
            }
            if (thisCounter == 18) {
                hearts.set(2, heartEmpty);
            }
            System.out.println(thisCounter);
        }
        PlayState.hasHit = false;
    }


    public void updateScore() {
        if (getTimeElapsed() < 2) {
            currentScore = "0" + getTimeElapsed() + "   Point";
        }
        if (getTimeElapsed() > 1 && getTimeElapsed() < 10) {
            currentScore = "0" + getTimeElapsed() + "   Points";

        } else {
            currentScore = "" + getTimeElapsed() + "   Points";
        }
        if (PlayState.hasHit) {

            updateHeart();
        }
        PlayState.hasHit = false;

        }


}
