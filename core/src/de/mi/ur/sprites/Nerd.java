package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import de.mi.ur.states.PlayState;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class Nerd {

    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    public static boolean jumpFinished;
    private enum State {RUNNING, FALLING, JUMPING}
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private State currentState;
    private State previousState;
    private Texture texture;
    //irgendwie auslagern?? COnstants.
    private Texture ground;
    private static int GROUND_Y_OFFSET = -30;

    private Animation birdAnimation;


    //private Texture bird;

    public Nerd(int x, int y) {
        texture = new Texture("birdanimation.png");
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        ground = new Texture("ground.png");
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());

        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
    }


    public void update(float dt) {
        birdAnimation.update(dt);
        if (position.y > 0) {
            //nur auf der y-Achse brauchen wir Schwerkraft
            velocity.add(0, GRAVITY, 0);
        }
        //mulitpliziert alles mit delta-time
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if (position.y <= ground.getHeight() + GROUND_Y_OFFSET) {
            position.y = ground.getHeight() + GROUND_Y_OFFSET;
            jumpFinished = true;
        }
        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);


    }

    public State getState () {
        if (position.y >0) {
            return State.JUMPING;
        } else if (position.y < 0) {
            return State.FALLING;
        }
        return State.RUNNING;

    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void jump() {
        velocity.y = 400;
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

}
