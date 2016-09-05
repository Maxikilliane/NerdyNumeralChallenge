package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by maxiwindl on 05.09.16.
 */
public class AnswerPhones {


    private Vector2 position;

    private Rectangle bounds;
    private Texture texture;

    private Animation phoneAnimation;


    public boolean colliding;


    public AnswerPhones(int x, int y) {
        position = new Vector2(x, y);


        texture = new Texture("phone_answer_new_1.png");
        phoneAnimation = new Animation(new TextureRegion(texture), 3, 0.8f);

        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        colliding = false;
    }

    public boolean collides(Rectangle player) {
        // return super.collides(player);
        return player.overlaps(bounds);
    }

    public void update(float dt) {
        phoneAnimation.update(dt);

    }


    public Vector2 getPosition() {
        return position;
    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return texture.getWidth();
    }

    public float getHeight() {
        return texture.getHeight();
    }

    public TextureRegion getTexture() {
        return phoneAnimation.getFrame();
    }


}
