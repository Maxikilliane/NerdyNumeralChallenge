package de.mi.ur.gameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
    private static long currentBasicScorePoints;
    private static Texture heartFilled;
    public static Array<Texture> hearts;
    private long startTime;
    private static int pointUpdate;
    private static Sound powerUp;
    BitmapFont scoreFont;
    private static Sound fail;
    private static Texture heartEmpty;


    public static int state;

    public Score() {
        hearts = new Array<Texture>();
        powerUp = Gdx.audio.newSound(Gdx.files.internal("powerupsfx.ogg"));
        state = 4;
        fail = Gdx.audio.newSound(Gdx.files.internal("failsfx.ogg"));
        currentScore = "Score: 0";
        currentBasicScorePoints = 0;
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

    private long getCurrentBasicScorePoints() {
        return currentBasicScorePoints;
    }

    public long getCurrentScorePoints() {
        return getCurrentScore() + getCurrentBasicScorePoints();
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
            state = ConstantsGame.HEARTSTATE_2_HEARTS;
                PlayState.alreadChanged = true;
            return state;
        } else if (!PlayState.alreadChanged && hearts.get(0) == heartEmpty && hearts.get(1) == heartEmpty && hearts.get(2) == heartFilled) {
            state = ConstantsGame.HEARTSTATE_1_HEART;
                PlayState.alreadChanged = true;
            return state;
        } else if (!PlayState.alreadChanged && hearts.get(0) == heartEmpty && hearts.get(1) == heartEmpty && hearts.get(2) == heartEmpty) {

            state = ConstantsGame.HEARTSTATE_NO_HEART;
                PlayState.alreadChanged = true;
            return state;
        } else if (!PlayState.alreadChanged && hearts.get(0) == heartFilled && hearts.get(1) == heartFilled && hearts.get(2) == heartFilled) {
                PlayState.alreadChanged = true;
            state = ConstantsGame.HEARTSTATE_ALL_HEARTS_FULL;
            return state;
        } else {
            return ConstantsGame.HEARTSTATE_OTHER;
        }


        }


    public static void updateHeart(GameStateManager manager, boolean dead) {
        state = getStateOfHearts();
        fail.play(0.5f);
        System.out.println("State of hearts: " + state);
        if (state == ConstantsGame.HEARTSTATE_ALL_HEARTS_FULL) {
            changeHeart(dead, 0);
        } else if (state == ConstantsGame.HEARTSTATE_NO_HEART) {
            manager.set(new GameOverState(manager));
        } else if (state == ConstantsGame.HEARTSTATE_1_HEART) {
            changeHeart(dead, 2);
            // System.out.println(2);
        } else if (state == ConstantsGame.HEARTSTATE_2_HEARTS) {
            changeHeart(dead, 1);
        } else {
            // dieses else tritt ziemlich oft auf! :)
        }


    }

    public static void refillHeart() {
        state = getStateOfHearts();
        powerUp.play(0.5f);
        System.out.println("State of hearts: " + state);
        if (state == ConstantsGame.HEARTSTATE_ALL_HEARTS_FULL) {
            addPoints();
        } else if (state == ConstantsGame.HEARTSTATE_NO_HEART) {
            changeHeart(false, 2);
        } else if (state == ConstantsGame.HEARTSTATE_1_HEART) {
            changeHeart(false, 1);
        } else if (state == ConstantsGame.HEARTSTATE_2_HEARTS) {
            changeHeart(false, 0);
        } else {
            // dieses else tritt ziemlich oft auf! :)
        }


    }


    public static void addPoints() {
        currentBasicScorePoints += 10;
    }

    public void updateScore(GameStateManager manager) {
        if (getTimeElapsed() < 2) {
            currentScore = "0" + getTimeElapsed() + "   Point";
        }
        if (getTimeElapsed() > 1 && getTimeElapsed() < 10) {
            currentScore = "0" + getTimeElapsed() + "   Points";

        } else {
            currentScore = "" + (getTimeElapsed() + currentBasicScorePoints) + "   Points";
        }

        PlayState.hasHit = false;


    }


}

