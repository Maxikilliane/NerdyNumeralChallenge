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
import de.mi.ur.states.GameStateManager;
import de.mi.ur.states.MenueState;
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
    public static int thisCounter = 0;

    public static int state;

    public Score() {
        hearts = new Array<Texture>();
        currentScore = "Score: 0";
        scoreFont = new BitmapFont(Gdx.files.internal("goodTimesNew.fnt"));
        heartFilled = new Texture("heart_filled.png");
        heartEmpty = new Texture("heart_empty_empty.png");
        scoreFont.setUseIntegerPositions(false);


    }

    public static void changeHeart(Boolean dead, int position) {
        if (dead) {
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

    public static int getStateOfHearts() {
       /* if (thisCounter/20 < 1) {
            return 1;
        }
        if (thisCounter/20 <2 && thisCounter/20 >1) {
            return 2;
        }
        if (thisCounter/20 > 2 && thisCounter <3 ) {
            return 3;
        } else {
            return 4;
        }*/
        if (!PlayState.alreadChanged) {
            if ((hearts.get(0) == heartEmpty) && (hearts.get(1) == heartFilled) && hearts.get(2) == heartFilled) {
                PlayState.alreadChanged = true;
                state = 1;
                return 1;

            } else if (hearts.get(1) == heartEmpty && hearts.get(2) == heartFilled && hearts.get(0) == heartEmpty) {
                PlayState.alreadChanged = true;
                state = 2;
                return 2;
            } else if (hearts.get(2) == heartEmpty && hearts.get(0) == heartEmpty && hearts.get(1) == heartEmpty) {
                PlayState.alreadChanged = true;
                state = 3;
                return 3;
            } else {
                PlayState.alreadChanged = true;
                state = 4;
                return 4;
            }
        } else {
            return 0;
        }
            /*
        if (!PlayState.alreadChanged) {
            if (hearts.get(1) == heartEmpty && hearts.get(2) == heartFilled && hearts.get(0) == heartEmpty) {
                PlayState.alreadChanged = true;
                state = 2;
                return 2;
            }

        }
        if (!PlayState.alreadChanged) {
            if (hearts.get(2) == heartEmpty && hearts.get(0) == heartEmpty && hearts.get(1) == heartEmpty) {
                PlayState.alreadChanged = true;
                state = 3;
                return 3;
            }
        }
        if (!PlayState.alreadChanged) {

            if ((hearts.get(0) == heartFilled) && (hearts.get(1) == heartFilled) && (hearts.get(2) == heartFilled)) {
                PlayState.alreadChanged = true;
                state = 4;
                return 4;
            }

        }*/
        //return state;
    }


    public static void updateHeart(GameStateManager manager) {
        if (getStateOfHearts() == 4) {
            System.out.println(4);

            changeHeart(true, 0);
        } else if (getStateOfHearts() == 3) {
            manager.set(new MenueState(manager));
            System.out.println("Ätschi");
        } else if (getStateOfHearts() == 2) {
            changeHeart(true, 2);
            System.out.println(2);
        } else if (getStateOfHearts() == 1) {
            changeHeart(true, 1);
            System.out.println(1);
        } else {
            // dieses else tritt ziemlich oft auf! :)
        }


        thisCounter++;
        System.out.println(thisCounter);
        System.out.println("counter geteilt: " + thisCounter / 20);
        if (thisCounter / 10 == 1) {
            hearts.set(0, heartEmpty);

        }
        if (thisCounter / 10 == 2) {
            hearts.set(1, heartEmpty);
        }
        if (thisCounter / 10 == 3) {
            hearts.set(2, heartEmpty);
        }
        System.out.println(thisCounter);

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
            currentScore = "" + getTimeElapsed() + pointUpdate + "   Points";
        }
        currentScorePoints = getTimeElapsed();
        if (PlayState.hasHit) {

            updateHeart(manager);
        }
        PlayState.hasHit = false;


    }


}

