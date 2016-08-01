package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class Pit {

    private Random random;
    private Vector3 position;
    private Texture pit;
    private Vector3 velocity;
    private static final int MOVEMENT = 100;


    public Pit () {
        pit = new Texture("pit.png");
        position = new Vector3(0, 0, 0);
        velocity = new Vector3(0, 0, 0);
    }

    public Texture getTexture () {
        return pit;
    }

    private void createNewPit() {
        random = new Random();
        //int pitWidth = random.nextInt()

    }

    public int getWidth () {
        return pit.getWidth();
    }

    public void update(float dt) {

        //mulitploziert alles mit delta-time
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if (position.y <= pit.getHeight()) {
            position.y = pit.getHeight();
        }
        velocity.scl(1 / dt);



    }






}
