package de.mi.ur;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.AndroidCommunication.WeatherDataListener;
import de.mi.ur.DataBase.NNCDatabase;
import de.mi.ur.QuestionLogic.MultipleChoiceQuestion;

public class AndroidLauncher extends AndroidApplication implements MultipleChoiceListener, WeatherDataListener {
	private int currentWeather;
	private NNCDatabase db;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			currentWeather = extras.getInt(Constants.CURRENT_WEATHER);
		}
		db = new NNCDatabase(this);
		db.open();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new NerdyNumeralChallenge(this, this, db), config);
	}

	@Override
	public String[] getQuestionInfos(int numeral1Base, int numeral2Base, int maxDigits, int difficulty) {
		MultipleChoiceQuestion question = new MultipleChoiceQuestion(numeral1Base, numeral2Base, maxDigits);
		String[] multipleChoiceQuestionInfos = {question.getQuestionNumber(), question.getRightAnswerString()};
		MultipleChoiceQuestion question = new MultipleChoiceQuestion(numeral1Base, numeral2Base, maxDigits);
		String[] multipleChoiceQuestionInfos = {question.getQuestionNumber(), question.getRightAnswerNumber()};
		return multipleChoiceQuestionInfos;
	}

	@Override
	public int getCurrentWeather() {
		return currentWeather;
	}


	/**
     * Created by Lydia on 15.08.2016.
     */

}
