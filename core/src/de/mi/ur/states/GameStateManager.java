package de.mi.ur.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class GameStateManager {
    //manages the states of the game. E.g. the player hits pause and a new state is put ontop. WHen the player presses
    //play again the pause state gets removed and the playstate is back.
    private Stack<State> states;

    public GameStateManager() {
        states = new Stack <State>();

    }
    public void push(State state) {
        states.push(state);
    }

    public void pop () {
        states.pop().dispose();
    }

    public void set (State state) {
        states.pop().dispose();
        states.push(state);

    }

    public void update (float flt) {
        states.peek().update(flt);
    }
    public void render (SpriteBatch batch) {
        states.peek().render(batch);
    }

}