package de.mi.ur.gameLogic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import de.mi.ur.ConstantsGame;

/**
 * Created by maxiwindl on 07.08.16.
 */
public class Score {

    private int score;
    private String currentScore;
    private long startTime;
    BitmapFont scoreFont;



    public Score() {
        score = 0;
        currentScore = "score: 0";
        scoreFont = new BitmapFont();

    }

    public void renderScore(SpriteBatch batch, OrthographicCamera cam) {
       scoreFont.draw(batch, currentScore, cam.position.x + ConstantsGame.SCORE_OFFSET_X, cam.position.y+ConstantsGame.SCORE_OFFSET_Y);
    }

    public long startTimer() {
      startTime = TimeUtils.millis();
        return startTime;
    }

    private long getTimeElapsed () {
        return TimeUtils.timeSinceMillis(startTime)/1000;
    }


    public void updateScore() {

            currentScore = "score: " + getTimeElapsed() +" sec";


    }
}
