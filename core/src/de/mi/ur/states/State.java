package de.mi.ur.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.ConstantsGame;

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
        cam.setToOrtho(false, ConstantsGame.DEFAULT_CAM_WIDTH, ConstantsGame.DEFAULT_CAM_HEIGHT);
    }

    protected abstract void handleInput ();

    public abstract void update (float flt);

    public abstract void render (SpriteBatch spriteBatch);
    public abstract void dispose ();
}
