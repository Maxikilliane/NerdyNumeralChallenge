package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.mi.ur.sprites.Nerd;
import de.mi.ur.sprites.Pit;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class PlayState extends State {

    private Nerd bird;
    private Texture background;
    private Texture ground;
    private Pit pit;
    private Vector2 pitPos1, pitPos2;
    private Vector2 groundPos1, groundPos2;
    private static int GROUND_Y_OFFSET = -30;


    protected PlayState(GameStateManager gameManager) {
        super(gameManager);
        bird = new Nerd(40, 200);
        background = new Texture("background_final.png");

        pit = new Pit();
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        pitPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        pitPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + pit.getWidth()/2, GROUND_Y_OFFSET);
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
        pit.update(dt);
        bird.update(dt);
        cam.position.set(bird.getX() + 80, cam.viewportHeight / 2, 0);
        cam.update();
    }

    @Override
    //draws things on the screen
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        spriteBatch.draw(pit.getTexture(), pitPos1.x, pitPos2.y);
        spriteBatch.draw(bird.getTexture(), bird.getX(), bird.getY());

        spriteBatch.end();

    }

    @Override
    public void dispose() {
        bird.dispose();
        background.dispose();
        ground.dispose();

    }
}
