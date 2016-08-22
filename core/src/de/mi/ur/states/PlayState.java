package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.GameQuestion;
import de.mi.ur.gameLogic.Score;
import de.mi.ur.sprites.Nerd;
import de.mi.ur.sprites.Pit;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class PlayState extends State {

    private Nerd nerd;
    private Texture background;
    private Texture ground;
    private Score score;
    private Random random;
    private GameQuestion gameQuestion;

    private Array<Pit> pits;
    private Vector2 groundPos1, groundPos2;


    protected PlayState(GameStateManager gameManager) {
        super(gameManager);
        nerd = new Nerd(ConstantsGame.NERD_X, ConstantsGame.NERD_Y);
        background = new Texture("background_final.png");
        score = new Score();
        random = new Random();
        gameQuestion = new GameQuestion();
        pits = new Array<Pit>();
        for (int i = 0; i < 4; i++) {
            pits.add(new Pit(i * (ConstantsGame.PIT_OFFSET + ConstantsGame.PIT_WIDTH)));

        }
        score.startTimer();
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, ConstantsGame.GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), ConstantsGame.GROUND_Y_OFFSET);

    }

    @Override
    protected void handleInput() {
        if (Nerd.jumpFinished) {
            if (Gdx.input.justTouched()) {
                nerd.jump();
                Nerd.jumpFinished = false;


            }

        }

    }


    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

    private void updatePits() {
        for (int i = 0; i < pits.size; i++) {
            Pit pit = pits.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > pit.getPitPos1().x + pit.getPit().getWidth()) {
                pit.reposition(pit.getPitPos1().x + ((pit.getPit().getWidth()) + generateNewDistance()) * 4);
            }
            if (pit.collides(nerd.getBounds()))
                gameManager.set(new MenueState(gameManager));
        }

    }


    private int generateNewDistance() {
        int newInt = random.nextInt(270);

        if (newInt >= 190) {
            return newInt;
        } else {
            return generateNewDistance();
        }

    }

    @Override
    //calculations for the render method
    public void update(float dt) {
        handleInput();
        updateGround();
        nerd.update(dt, ConstantsGame.NERD_GRAVITY_DEFAULT, increaseDifficulty());
        score.updateScore();
        gameQuestion.updateQuestions(cam);

        cam.position.x = nerd.getPosition().x + ConstantsGame.NERD_POSITION_OFFSET;
        updatePits();
        cam.update();
    }

    private int increaseDifficulty() {
        long value = score.getCurrentScore();
        if (value > 50) {
            return 130;
        }
        if (value > 100) {
            return 160;
        }
        if (value > 150) {
            return 190;
        }
        if (value > 200) {
            return 220;
        }
        if (value > 250) {
            return 250;
        }
        if (value > 300) {
            return 280;

        } else {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT;
        }
    }


    @Override
    //draws things on the screen
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        score.renderScore(spriteBatch, cam);
        gameQuestion.drawTasks(spriteBatch, cam);
        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        spriteBatch.draw(nerd.getTexture(), nerd.getX(), nerd.getY());
        for (Pit pit : pits) {
            spriteBatch.draw(pit.getPit(), pit.getPitPos1().x, pit.getPitPos1().y);
        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        nerd.dispose();
        background.dispose();
        ground.dispose();

    }
}
