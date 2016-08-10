package de.mi.ur;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class StartActivity extends Activity {

    private Button buttonTutorial;
    private Button buttonPractice;
    private Button buttonGame;
    private Button buttonProgress;

    private TextView test;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        setupUI();

        /* // Testing Superscript
        test = (TextView) findViewById(R.id.testView);
        test.setText(Html.fromHtml(getResources().getString(R.string.testString)));
*/

    }

    private void setupUI() {
        buttonTutorial = (Button) findViewById(R.id.start_tutorial_button);
        setOnClickListener (buttonTutorial);
        buttonTutorial.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, TutorialMainActivity.class);
                startActivity(i);
            }
        });
        buttonPractice = (Button) findViewById(R.id.start_practice_button);
        setOnClickListener(buttonPractice);
        buttonGame = (Button) findViewById(R.id.start_game_button);
        buttonGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent (StartActivity.this, GameMainActivity.class);
                startActivity(i);
            }
        });
        setOnClickListener(buttonGame);
        buttonProgress = (Button) findViewById(R.id.start_progress_button);
        setOnClickListener(buttonProgress);
    }

    public void setOnClickListener(View v) {
        /*Intent i = null;
        switch (v.getId()){
            case R.id.start_tutorial_button:
                i = new Intent(StartActivity.this, TutorialMainActivity.class);
                break;
            case R.id.start_practice_button:
                   // i = new Intent (StartActivity.this, :
                break;
            case R.id.start_game_button:
                i= new Intent (StartActivity.this, GameMainActivity.class);
                break;
            case R.id.start_progress_button:
                //i = new Intent (StartActivity.this, )
                break;
            default:
                break;
        }
        if(i!=null){
            startActivity(i);
        }*/

    }

 // Meiner Meinung nach elegantere Lösung für das Verbinden der Buttons mit Click Listener, man
    // müsste nur oben noch implements OnClickListener schreiben

    /*
    private void setUpUI(){
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
        switch(v.getId()){
            case R.id.start_tutorial_button:
                i = new Intent(StartActivity.this, TutorialMainActivity.class);
                break;
            case R.id.start_practice_button:

                break;
            case R.id.start_game_button:
                i = new Intent (StartActivity.this, GameMainActivity.class);
                break;
            case R.id.start_progress_button:

                break;
            default:
                break;
        }

        if(i!= null){
            startActivity(i);
        }
    }

}*/
}
