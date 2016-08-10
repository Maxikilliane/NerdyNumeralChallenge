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



    private Texture pit;
    private Random random;
    private Rectangle bounds;
    private Vector2 pitPos;


    public Pit (float x) {

        pit = new Texture("pit_new.png");
        random = new Random();
        pitPos = new Vector2((x+generateNewDistance() + pit.getWidth()) * 2, 0);
        bounds = new Rectangle(getPitPos1().x, getPitPos1().y, pit.getWidth()- ConstantsGame.PIT_BOUNDS_OFFSET, pit.getHeight());


    }

    public Texture getPit () {
        return pit;
    }
    private int generateNewDistance() {
        int newInt = random.nextInt(50);

            return newInt;




    }

    public Vector2 getPitPos1() {
        return pitPos;
    }

    public boolean collides(Rectangle player){
        return player.overlaps(bounds);
    }


    public void reposition(){

        pitPos.set((pitPos.x+generateNewDistance() + pit.getWidth()) * 2, 0);
        bounds.setPosition(pitPos.x,getPitPos1().y);

    }












}
