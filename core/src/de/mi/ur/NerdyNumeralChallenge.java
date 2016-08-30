package de.mi.ur;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.AndroidCommunication.WeatherDataListener;
import de.mi.ur.states.GameStateManager;
import de.mi.ur.states.MenueState;

public class NerdyNumeralChallenge extends ApplicationAdapter {

	public static final int WIDTH = 480;
	public  static final int HEIGHT = 800;
	private SpriteBatch batch;
	private GameStateManager manager;

	private int currentWeather;
	private MultipleChoiceListener questionGenerator;
	private WeatherDataListener weatherDataListener;


	public NerdyNumeralChallenge(MultipleChoiceListener multipleChoiceListener, WeatherDataListener weatherDataListener){
		questionGenerator = multipleChoiceListener;
		this.weatherDataListener = weatherDataListener;

		currentWeather = weatherDataListener.getCurrentWeather();
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new GameStateManager(questionGenerator, weatherDataListener);
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
