package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
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
    private Rectangle bounds;
    private Vector2 pitPos1, pitPos2;


    public Pit (float x) {

        pit = new Texture("pit_new.png");
        position = new Vector3(0, 0, 0);
        velocity = new Vector3(0, 0, 0);
        random = new Random();
        pitPos1 = new Vector2(x, 0);
        bounds = new Rectangle(getPitPos1().x, getPitPos1().y, pit.getWidth()- ConstantsGame.PIT_BOUNDS_OFFSET, pit.getHeight());


    }
    public Texture getPit () {
        return pit;
    }

    public Vector2 getPitPos1() {
        return pitPos1;
    }

    public boolean collides(Rectangle player){
        return player.overlaps(bounds);
    }


    public void reposition(float x){

        pitPos1.set(x, 0);
        bounds.setPosition(x,getPitPos1().y);

    }












}
