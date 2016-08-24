package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import de.mi.ur.ConstantsGame;

/**
 * Created by Anna-Marie on 19.08.2016.#
 * Hoffe, das ist okay so... Ich bin noch nicht wirklich drin. Habs heut aber mal probiert. V.a mit der y-Position war ich mir nicht sicher,
 * hab aber jetzt mal die vom Nerd genommen, die Dame soll ja auch auf dem Boden sitzen, bzw. ihre Bank soll auf dem Boden stehen.
 */
public class Obstacle {

    private Texture obstacle;
    private Random random;
    private Rectangle bounds;
    private Vector2 obstaclePos;


    public Obstacle(float x) {

        obstacle = new Texture("obstacle.png");
        random = new Random();
        obstaclePos = new Vector2(x, ConstantsGame.NERD_Y);
        bounds = new Rectangle(obstaclePos.x, obstaclePos.y, obstacle.getWidth() - ConstantsGame.PIT_BOUNDS_OFFSET, obstacle.getHeight());

    }

    public Texture getObstacle() {
        return obstacle;
    }

    private int generateNewDistance() {
        int newInt = random.nextInt(4);

        return newInt;
    }

    public Vector2 getObstaclePos1() {
        return obstaclePos;
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }

    public void reposition(float x) {

        obstaclePos.set(x, 0);
        bounds.setPosition(obstaclePos.x, obstaclePos.y);

    }


}
