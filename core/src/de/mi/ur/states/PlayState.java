package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.Score;
import de.mi.ur.sprites.Nerd;
import de.mi.ur.sprites.Pit;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class PlayState extends State {

    private Nerd bird;
    private Texture background;
    private Texture ground;
    private Score score;


    private Array<Pit> pits;
    private Vector2 groundPos1, groundPos2;
    private Random random;
    private int newInt;


    protected PlayState(GameStateManager gameManager) {
        super(gameManager);
        bird = new Nerd(40, 200);
        background = new Texture("background_final.png");
        random = new Random();
        score = new Score();
        pits = new Array<Pit>();
        for (int i = 1; i <= 4; i++) {
            newInt = generateNewDistance();
            pits.add(new Pit(i * (newInt + ConstantsGame.PIT_WIDTH)));

        }
        score.startTimer();
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, ConstantsGame.GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), ConstantsGame.GROUND_Y_OFFSET);

    }

    private int generateNewDistance() {
        int newInt = random.nextInt(180);
        if (newInt > 130) {
            return newInt;
        } else {
            return newInt + generateNewDistance();
        }
    }

    @Override
    protected void handleInput() {
        if (Nerd.jumpFinished) {
            if (Gdx.input.justTouched()) {
                bird.jump();
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

    @Override
    //calculations for the render method
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        score.updateScore();
        cam.position.x = bird.getPosition().x + 80;
        for (int i = 0; i < pits.size; i++) {
            Pit pit = pits.get(i);
            newInt = generateNewDistance();
            if (cam.position.x - (cam.viewportWidth / 2) > pit.getPitPos1().x + pit.getPit().getWidth()) {
                pit.reposition(pit.getPitPos1().x + newInt * 4);
            }
            if (pit.collides(bird.getBounds()))
                gameManager.set(new MenueState(gameManager));
        }
        cam.update();
    }


    @Override
    //draws things on the screen
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        score.renderScore(spriteBatch, cam);
        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        spriteBatch.draw(bird.getTexture(), bird.getX(), bird.getY());
        for (Pit pit : pits) {
            spriteBatch.draw(pit.getPit(), pit.getPitPos1().x, pit.getPitPos1().y);
        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        bird.dispose();
        background.dispose();
        ground.dispose();

    }
}
