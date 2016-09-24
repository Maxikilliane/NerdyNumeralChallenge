package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.Score;
import de.mi.ur.states.GameStateManager;

/**
 * Created by maxiwindl on 05.09.16.
 */
public class AnswerPhone {


    private Vector2 position;


    private Rectangle bounds;
    private static boolean counted;


    private Animation phoneAnimation;
    private Animation phoneAnimation2;


    public boolean colliding;


    public AnswerPhone(int x, int y, Texture texture) {
        position = new Vector2(x, y);

        counted = true;
        phoneAnimation = new Animation(new TextureRegion(texture), 3, 0.8f);

        bounds = new Rectangle(x, y, texture.getWidth() / 3 + ConstantsGame.BOUNDS_OFFSET, texture.getHeight());
        colliding = false;
    }

    public boolean isCounted() {
        return counted;
    }

    public void setCounted() {
        counted = true;
    }

    public static void resetCounted() {
        counted = false;
    }

    public boolean collides(Rectangle player) {
        // return super.collides(player);

        return player.overlaps(bounds);
    }

    public void update(float dt) {
        phoneAnimation.update(dt);
        bounds.setPosition(position.x, position.y);


    }


    public Vector2 getPosition() {
        return position;
    }


    /*
     * state 1: 2 hearts full
     * state 2: 1 heart full
     * state 3: 0 hearts full
     * state 4: all hearts full
     */
    public void reactToCollision(GameStateManager manager) {

        int state = Score.getStateOfHearts();
        System.out.println(state);
        if (state == 4) {
            //Score.changeHeart(false, 0);
        } else if (state == 3) {
            Score.changeHeart(false, 2);
        } else if (state == 2) {
            Score.changeHeart(false, 1);
            System.out.println(2);
        } else if (state == 1) {
            Score.changeHeart(false, 0);
        } else {
            // dieses else tritt ziemlich oft auf! :)
        }

    }

    public void reactToWrongCollision(GameStateManager manager) {
        System.out.println(Score.getStateOfHearts());

        Score.updateHeart(manager, true);

    }
    public float getX() {
        return position.x;
    }


    public float getY() {
        return position.y;
    }


    public TextureRegion getTexture() {
        return phoneAnimation.getFrame();
    }


}
