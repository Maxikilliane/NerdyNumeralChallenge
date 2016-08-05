package de.mi.ur.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by maxiwindl on 31.07.16.
 */
public abstract class State {
    //camera to locate a position in the world
    public static OrthographicCamera cam;

    protected GameStateManager gameManager;

    protected State (GameStateManager gameManager) {
        this.gameManager = gameManager;
        cam = new OrthographicCamera();
        //magic numbers vermeiden später
        cam.setToOrtho(false, 240, 400);


    }

    protected abstract void handleInput ();

    public abstract void update (float flt);
    public abstract void render (SpriteBatch spriteBatch);
    public abstract void dispose ();
}
