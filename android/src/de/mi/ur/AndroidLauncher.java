package de.mi.ur;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import de.mi.ur.AndroidCommunication.DialogListener;
import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.AndroidCommunication.WeatherDataListener;
import de.mi.ur.DataBase.NNCDatabase;
import de.mi.ur.QuestionLogic.MultipleChoiceQuestion;

public class AndroidLauncher extends AndroidApplication implements MultipleChoiceListener, WeatherDataListener, DialogListener {
	private int currentWeather;
	private NNCDatabase db;
	private HighscoreDialog highscoreDialog;
	private MultipleChoiceDialog multipleChoiceDialog;
	//private static String userName = "Nerd";
	private boolean backgroundMusic;
	private boolean soundEffects;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			currentWeather = extras.getInt(Constants.CURRENT_WEATHER);
			backgroundMusic = extras.getBoolean(Constants.BACKGROUND_MUSIC,true);
			soundEffects = extras.getBoolean(Constants.SOUND_EFFECTS,true);
			System.out.println("AndroidLaunecher: BackgorundMusik: " + backgroundMusic + " SoundEffects: "+ soundEffects);
		}
		db = new NNCDatabase(this);
		db.open();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new NerdyNumeralChallenge(this, this, db, this), config);
	}

	@Override
	public String[] getQuestionInfos(int numeral1Base, int numeral2Base, int maxDigits, int difficulty) {
		MultipleChoiceQuestion question = new MultipleChoiceQuestion(numeral1Base, numeral2Base, maxDigits);
		String[] possAnswers = question.generatePossAnswers();
		String[] multipleChoiceQuestionInfos = {question.getQuestion(), question.getRightAnswerString(), possAnswers[0], possAnswers[1], possAnswers[2], possAnswers[3]};
		return multipleChoiceQuestionInfos;
	}

	@Override
	public int getCurrentWeather() {
		return currentWeather;
	}

	@Override
	public void showHighscoreDialog() {
		highscoreDialog = new HighscoreDialog();
		highscoreDialog.show(getFragmentManager(), "My HighscoreDialog");
	}

	@Override
	public boolean getDialogDone() {
		if (highscoreDialog != null) {
			return highscoreDialog.getDialogDone();
		} else {
			return true;
		}

	}

	@Override
	public String getUserName() {
		return highscoreDialog.getUserName();
	}


	@Override
	public void showMultipleChoiceDialog() {
		multipleChoiceDialog = new MultipleChoiceDialog();
		multipleChoiceDialog.show(getFragmentManager(), "My MultipleChoiceDialog");
	}

	@Override
	public void dismissDialog() {
		multipleChoiceDialog.dismissDialog();
	}

	@Override
	public boolean getRightDialogAnswer() {
		return multipleChoiceDialog.getRightAnswer();
	}

	@Override
	public boolean getWrongDialogAnswer() {
		return multipleChoiceDialog.getWrongAnswer();
	}

	@Override
	public boolean getBackgroundMusic() {
		return backgroundMusic;
	}

	@Override
	public boolean getSoundEffects() {
		return soundEffects;
	}
}
