package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.Score;

/**
 * Created by maxiwindl on 05.09.16.
 */
public class AnswerPhones {


    private Vector2 position;


    private Rectangle bounds;


    private Animation phoneAnimation;
    private Animation phoneAnimation2;


    public boolean colliding;


    public AnswerPhones(int x, int y, Texture texture) {
        position = new Vector2(x, y);



        phoneAnimation = new Animation(new TextureRegion(texture), 3, 0.8f);

        bounds = new Rectangle(x, y, texture.getWidth() / 3 + ConstantsGame.BOUNDS_OFFSET, texture.getHeight());
        colliding = false;
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

    public void reactToCollision() {
        if (Score.getStateOfHearts() == 4) {
            //Score.addPoints();
        }
        if (Score.getStateOfHearts() == 3) {
            Score.refillHeart(false, Score.getStateOfHearts() - 1);
        }
        if (Score.getStateOfHearts() == 2) {
            Score.refillHeart(false, Score.getStateOfHearts() - 1);
        }
        if (Score.getStateOfHearts() == 1) {
            Score.refillHeart(false, Score.getStateOfHearts() - 1);
        }
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
