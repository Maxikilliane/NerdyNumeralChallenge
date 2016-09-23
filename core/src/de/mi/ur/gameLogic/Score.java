package de.mi.ur.gameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import de.mi.ur.ConstantsGame;
import de.mi.ur.states.GameOverState;
import de.mi.ur.states.GameStateManager;
import de.mi.ur.states.PlayState;


/**
 * Created by maxiwindl on 07.08.16.
 */
public class Score {


    private static String currentScore;
    private long currentScorePoints;
    private static Texture heartFilled;
    public static Array<Texture> hearts;
    private long startTime;
    private static int pointUpdate;
    BitmapFont scoreFont;
    private static Texture heartEmpty;


    public static int state;

    public Score() {
        hearts = new Array<Texture>();
        state = 4;
        currentScore = "Score: 0";
        scoreFont = new BitmapFont(Gdx.files.internal("goodTimesNew.fnt"));
        heartFilled = new Texture("heart_filled.png");
        heartEmpty = new Texture("heart_empty.png");
        scoreFont.setUseIntegerPositions(false);


    }

    public static void changeHeart(Boolean isDead, int position) {
        if (isDead) {
            hearts.set(position, heartEmpty);
        } else {
            hearts.set(position, heartFilled);
        }
    }

    public static Texture getHeartFilled() {
        return heartFilled;
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

    public long getCurrentScorePoints() {
        return currentScorePoints;
    }

    public long startTimer() {
        startTime = TimeUtils.millis();
        return startTime;
    }

    private long getTimeElapsed() {
        return TimeUtils.timeSinceMillis(startTime) / 1000;
    }

    /*
     * state 1: 2 hearts full
     * state 2: 1 heart full
     * state 3: 0 hearts full
     * state 4: all hearts full
     */
    public static int getStateOfHearts() {

        if ((!PlayState.alreadChanged && hearts.get(0) == heartEmpty) && (hearts.get(1) == heartFilled) && hearts.get(2) == heartFilled) {
            state = 1;
                PlayState.alreadChanged = true;
            return state;
        } else if (!PlayState.alreadChanged && hearts.get(0) == heartEmpty && hearts.get(1) == heartEmpty && hearts.get(2) == heartFilled ) {
                state = 2;
                PlayState.alreadChanged = true;
            return state;
        } else if (!PlayState.alreadChanged && hearts.get(0) == heartEmpty && hearts.get(1) == heartEmpty && hearts.get(2) == heartEmpty) {

                state = 3;
                PlayState.alreadChanged = true;
            return state;
        } else  if(!PlayState.alreadChanged && hearts.get(0)== heartFilled && hearts.get(1) == heartFilled && hearts.get(2) == heartFilled){
                PlayState.alreadChanged = true;
            state = 4;
            return state;
            }else{
            return 5;
        }


        }


    public static void updateHeart(GameStateManager manager, boolean dead) {
        state = getStateOfHearts();
        System.out.println("State of hearts: "+state);
        if (state == 4) {
            changeHeart(dead, 0);
        } else if (state == 3) {
            manager.set(new GameOverState(manager));
        } else if (state == 2) {
            changeHeart(dead, 2);
           // System.out.println(2);
        } else if (state == 1) {
            changeHeart(dead, 1);
        } else {
            // dieses else tritt ziemlich oft auf! :)
        }


    }

    public static void refillHeart() {
        state = getStateOfHearts();
        System.out.println("State of hearts: "+state);
        if (state == 4) {
            //changeHeart(dead, 0);
        } else if (state == 3) {
            changeHeart(false, 2);
        } else if (state == 2) {
            changeHeart(false, 1);
        } else if (state == 1) {
            changeHeart(false, 0);
        } else {
            // dieses else tritt ziemlich oft auf! :)
        }


    }


    public static void addPoints() {
        pointUpdate = 10;
    }

    public void updateScore(GameStateManager manager) {
        if (getTimeElapsed() < 2) {
            currentScore = "0" + getTimeElapsed() + "   Point";
        }
        if (getTimeElapsed() > 1 && getTimeElapsed() < 10) {
            currentScore = "0" + getTimeElapsed() + "   Points";

        } else {
            currentScore = "" + getTimeElapsed() + "   Points";
        }
        currentScorePoints = getTimeElapsed();

        PlayState.hasHit = false;


    }


}

