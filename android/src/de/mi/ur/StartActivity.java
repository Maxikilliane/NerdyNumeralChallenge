package de.mi.ur;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class StartActivity extends Activity implements View.OnClickListener {

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
        buttonTutorial.setOnClickListener(this);
        buttonPractice = (Button) findViewById(R.id.start_practice_button);
        buttonPractice.setOnClickListener(this);
        buttonGame = (Button) findViewById(R.id.start_game_button);
        buttonGame.setOnClickListener(this);
        buttonProgress = (Button) findViewById(R.id.start_progress_button);
        buttonProgress.setOnClickListener(this);
    }


    //nicht vollständig implementiert (Practice und Progress fehlen
    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()){
            case R.id.start_tutorial_button:
                i = new Intent(StartActivity.this, TutorialMainActivity.class);
                break;
            case R.id.start_practice_button:
                break;
            case R.id.start_game_button:
                i= new Intent (StartActivity.this, GameMainActivity.class);
                break;
            case R.id.start_progress_button:
                break;
            default:
                break;
        }
        if(i!=null){
            startActivity(i);
        }

    }
}
