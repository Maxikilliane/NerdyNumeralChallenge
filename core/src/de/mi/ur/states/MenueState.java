package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.NerdyNumeralChallenge;
import de.mi.ur.gameLogic.Score;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class MenueState extends State {

    private Texture background;
    private Texture playButton;


    public MenueState(GameStateManager gameManager) {
        super(gameManager);
        Score.thisCounter = 0;
        Score.state = 0;
        cam.setToOrtho(false, NerdyNumeralChallenge.WIDTH/2, NerdyNumeralChallenge.HEIGHT/2);
        background = new Texture("background_final.png");
        playButton = new Texture("playbtn.png");
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gameManager.set(new PlayState(gameManager));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0);
        spriteBatch.draw(playButton,cam.position.x - playButton.getWidth() / 2, cam.position.y);
        spriteBatch.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();

    }
}
