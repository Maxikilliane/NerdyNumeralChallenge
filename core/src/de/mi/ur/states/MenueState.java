package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.NerdyNumeralChallenge;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class MenueState extends State {

    private Texture background;
    private Texture playbutton;


    public MenueState(GameStateManager gameManager) {
        super(gameManager);
        cam.setToOrtho(false);
        background = new Texture("background_final.png");
        playbutton = new Texture("playbtn.png");
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gameManager.set(new PlayState(gameManager));
        }

    }

    @Override
    public void update(float flt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, NerdyNumeralChallenge.WIDTH, NerdyNumeralChallenge.HEIGHT);
        spriteBatch.draw(playbutton, (NerdyNumeralChallenge.WIDTH / 2) -
                (playbutton.getWidth() / 2), NerdyNumeralChallenge.HEIGHT / 2);
        spriteBatch.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playbutton.dispose();

    }
}
