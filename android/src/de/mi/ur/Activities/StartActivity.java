package de.mi.ur.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.mi.ur.R;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonTutorial;
    private Button buttonPractice;
    private Button buttonGame;
    private Button buttonProgress;

    private TextView test;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        setUpUI();
        setupToolbar();

         /* Testing Superscript - needs to be deleted
        test = (TextView) findViewById(R.id.testView);
        */


    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.start_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        //getSupportActionBar().setLogo(R.drawable.weather_button);
        //getSupportActionBar().setIcon(R.drawable.settings_actionbar_icon);

    }

    /*
    private void setupUI() {
        buttonTutorial = (Button) findViewById(R.id.start_tutorial_button);
        buttonTutorial.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, TutorialMainActivity.class);
                startActivity(i);
            }
        });
        buttonPractice = (Button) findViewById(R.id.start_practice_button);
        buttonGame = (Button) findViewById(R.id.start_game_button);
        buttonGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent (StartActivity.this, GameMainActivity.class);
                startActivity(i);
            }
        });

        buttonProgress = (Button) findViewById(R.id.start_progress_button);
        buttonProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, ProgressActivity.class);
                startActivity(i);
            }
        });

    }
*/


    // Meiner Meinung nach elegantere Lösung für das Verbinden der Buttons mit Click Listener, man
    // müsste nur oben noch implements OnClickListener schreiben


    private void setUpUI() {
        buttonTutorial = (Button) findViewById(R.id.start_tutorial_button);
        buttonTutorial.setOnClickListener(this);
        buttonPractice = (Button) findViewById(R.id.start_practice_button);
        buttonPractice.setOnClickListener(this);
        buttonGame = (Button) findViewById(R.id.start_game_button);
        buttonGame.setOnClickListener(this);
        buttonProgress = (Button) findViewById(R.id.start_progress_button);
        buttonProgress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.start_tutorial_button:
                i = new Intent(StartActivity.this, TutorialMainActivity.class);
                break;
            case R.id.start_practice_button:
                i = new Intent(StartActivity.this, PracticeMainActivity.class);
                break;
            case R.id.start_game_button:
                i = new Intent(StartActivity.this, GameMainActivity.class);
                break;
            case R.id.start_progress_button:
                i = new Intent(StartActivity.this, ProgressActivity.class);
                break;
            default:
                break;
        }

        if (i != null) {
            startActivity(i);
        }
    }


}
