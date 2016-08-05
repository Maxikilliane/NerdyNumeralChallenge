package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

import de.mi.ur.ConstantsGame;
import de.mi.ur.states.State;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class Pit {


    private Vector3 position;
    private Texture pit;
    private Vector3 velocity;
    private static final int MOVEMENT = 100;
    private Random random;
    private Vector2 pitPos1, pitPos2;


    public Pit (float x) {
        pit = new Texture("pit.png");
        position = new Vector3(0, 0, 0);
        velocity = new Vector3(0, 0, 0);
        random = new Random();
        pitPos1 = new Vector2(x, pitPos1.y - ConstantsGame.PIT_GAP - pit.getHeight());
       pitPos1 = new Vector2(State.cam.position.x - State.cam.viewportWidth / 2, ConstantsGame.PIT_Y_OFFSET);
        pitPos2 = new Vector2((State.cam.position.x - State.cam.viewportWidth / 2) + pit.getWidth()/2, ConstantsGame.PIT_Y_OFFSET);
    }

    public float getX() {
        return pitPos1.x;
    }

    public float getY() {
        return pitPos2.y;
    }

    public void reposition(float x){

        pitPos1.set(x, pitPos1.y - ConstantsGame.PIT_GAP - pit.getHeight());

    }

    public Texture getTexture () {
        return pit;
    }

    private void createNewPit() {
        random = new Random();

        int pitWidth = random.nextInt();

    }



    public int getWidth () {
        return pit.getWidth();
    }

   /* public void update(float dt) {
        if (State.cam.position.x - (State.cam.viewportWidth / 2) > pitPos1.x + pit.getWidth()+1) {
            pitPos1.add(pit.getWidth() * 2, 0);
        }
       if (State.cam.position.x - (State.cam.viewportWidth / 2) > pitPos2.x + pit.getWidth()+1) {
           pitPos2.add(pit.getWidth() * 2, 0);
       }





    }*/






}
