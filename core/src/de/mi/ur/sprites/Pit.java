package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import de.mi.ur.ConstantsGame;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class Pit extends Obstacle {



    private Texture pit;
    private Random random;
    private Rectangle bounds;
    private Vector2 pitPos;


    public Pit (float x) {
        super(x, 0, new Texture("pit_new.png"));
        pit = new Texture("pit_new.png");
        random = new Random();
        pitPos = new Vector2(x, 0);
        bounds = new Rectangle(pitPos.x, pitPos.y, pit.getWidth() + ConstantsGame.BOUNDS_OFFSET, pit.getHeight());


    }

    public Texture getPit () {
        return pit;
        // return super.getObstacle();
    }

    public Vector2 getPitPos() {

        //return super.getObstaclePos();
        return pitPos;
    }

    public boolean collides(Rectangle player){
        //return super.collides(bounds);
        return player.overlaps(bounds);
    }

    public void dispose() {
        // super.dispose();
        pit.dispose();
    }


    public void reposition(float x){
        //super.reposition(x);
        pitPos.set(x, 0);
        bounds.setPosition(pitPos.x, pitPos.y);

    }












}
