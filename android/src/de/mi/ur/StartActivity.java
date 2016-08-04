package de.mi.ur;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class StartActivity extends Activity {

    private Button buttonTutorial;
    private Button buttonPractice;
    private Button buttonGame;
    private Button buttonProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        setupUI();


    }

    private void setupUI() {
        buttonTutorial = (Button) findViewById(R.id.start_tutorial_button);
        setOnClickListener (buttonTutorial);
        buttonPractice = (Button) findViewById(R.id.start_practice_button);
        setOnClickListener(buttonPractice);
        buttonGame = (Button) findViewById(R.id.start_game_button);
        setOnClickListener(buttonGame);
        buttonProgress = (Button) findViewById(R.id.start_progress_button);
        setOnClickListener(buttonProgress);
    }

    private void setOnClickListener (Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {

            }
        });
    }
}
