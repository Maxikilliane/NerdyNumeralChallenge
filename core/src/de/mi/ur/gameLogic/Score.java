package de.mi.ur.gameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import de.mi.ur.ConstantsGame;

/**
 * Created by maxiwindl on 07.08.16.
 */
public class Score {


    private String currentScore;
    private Texture heartFilled;
    private Array<Texture> hearts;
    private long startTime;
    BitmapFont scoreFont;


    public Score() {
        hearts = new Array<Texture>();
        currentScore = "Score: 0";
        scoreFont = new BitmapFont();
        heartFilled = new Texture("heart_filled.png");
        scoreFont.setUseIntegerPositions(false);

    }

    public void renderScore(SpriteBatch batch, OrthographicCamera cam) {
        for (int i = 0; i < 3; i++) {

            hearts.add(heartFilled);
            batch.draw(hearts.get(i), cam.position.x + ConstantsGame.SCORE_HEARTS_OFFSET_X+i*hearts.get(i).getWidth(), cam.position.y + ConstantsGame.SCORE_HEARTS_OFFSET_Y);
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


    public void updateScore() {
        if (getTimeElapsed() < 2) {
            currentScore = "0" + getTimeElapsed() + " point";
        }
        if (getTimeElapsed() > 1 && getTimeElapsed() < 10) {
            currentScore = "0" + getTimeElapsed() + " points";

        } else {
            currentScore = "" + getTimeElapsed() + " points";
        }


    }
}
