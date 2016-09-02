package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.impl.orbutil.closure.Constant;

import java.util.Random;

import de.mi.ur.ConstantsGame;

/**
 * Created by Anna-Marie on 19.08.2016.#
 *
 * */
public abstract class Obstacle {

    private Texture texture;
    private Rectangle bounds;
    private Vector2 obstaclePos;
    private int obstacleY;
    private int type;


    public Obstacle(float x, int obstacleY, Texture texture, int type) {
        if(type == ConstantsGame.PIT_TYPE || type == ConstantsGame.WOMAN_TYPE){
            this.type = type;
        }else{
            this.type = ConstantsGame.PIT_TYPE;
        }
        this.texture = texture;
        this.obstacleY = obstacleY;
        obstaclePos = new Vector2(x, obstacleY);
        bounds = new Rectangle(obstaclePos.x, obstaclePos.y, texture.getWidth() - ConstantsGame.PIT_BOUNDS_OFFSET, texture.getHeight());

    }

    public Texture getTexture() {
        return texture;
    }

    public int getType(){
        return type;
    }

    public Vector2 getObstaclePos() {
        return obstaclePos;
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }

    public void reposition(float x) {

        obstaclePos.set(x, obstacleY);
        bounds.setPosition(obstaclePos.x, obstaclePos.y);

    }

    public void dispose() {
        texture.dispose();
    }


}
