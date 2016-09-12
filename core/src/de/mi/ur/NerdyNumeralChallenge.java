package de.mi.ur;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.AndroidCommunication.WeatherDataListener;
import de.mi.ur.states.GameStateManager;
import de.mi.ur.states.MenueState;

public class NerdyNumeralChallenge extends ApplicationAdapter {


	private SpriteBatch batch;
	private GameStateManager manager;

	private int currentWeather;
	private MultipleChoiceListener questionGenerator;
	private WeatherDataListener weatherDataListener;
	private HighscoreListener highscoreListener;


	public NerdyNumeralChallenge(MultipleChoiceListener multipleChoiceListener, WeatherDataListener weatherDataListener, HighscoreListener highscoreListener) {
		questionGenerator = multipleChoiceListener;
		this.weatherDataListener = weatherDataListener;
		this.highscoreListener = highscoreListener;

		currentWeather = weatherDataListener.getCurrentWeather();
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new GameStateManager(questionGenerator, weatherDataListener, highscoreListener);
		manager.push(new MenueState(manager));

		Gdx.gl.glClearColor(0, 0, 0, 0);
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
