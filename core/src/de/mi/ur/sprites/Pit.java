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

    public Pit () {
        pit = new Texture("pit.png");
    }

    private void createNewPit() {
        random = new Random();
        //int pitWidth = random.nextInt()

    }




}
