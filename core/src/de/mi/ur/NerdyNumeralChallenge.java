package de.mi.ur;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.states.GameStateManager;
import de.mi.ur.states.MenueState;

public class NerdyNumeralChallenge extends ApplicationAdapter {

	public static final int WIDTH = 1200;
	public  static final int HEIGHT = 880;
	private SpriteBatch batch;
	private Texture img;
	private GameStateManager manager;



	
	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new GameStateManager();
		manager.push(new MenueState(manager));
		Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     	manager.update(Gdx.graphics.getDeltaTime());
		manager.render(batch);

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}