package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.Score;

/**
 * Created by maxiwindl on 12.09.16.
 */
public class GameOverState extends State {

    private Texture playButton;
    private Texture gameOver;


    public GameOverState(GameStateManager gameManager) {
        super(gameManager);

        Score.thisCounter = 0;
        Score.state = 0;

        cam.setToOrtho(false, ConstantsGame.SCREEN_WIDTH, ConstantsGame.SCREEN_HEIGHT);

        gameOver = new Texture("game_over.png");


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
        spriteBatch.draw(gameOver, cam.position.x - gameOver.getHeight() / 2 - ConstantsGame.GAME_OVER_OFFSET_X, cam.position.y - ConstantsGame.GAME_OVER_OFFSET_Y);
        spriteBatch.end();

    }

    @Override
    public void dispose() {
        playButton.dispose();
        gameOver.dispose();


    }
}


