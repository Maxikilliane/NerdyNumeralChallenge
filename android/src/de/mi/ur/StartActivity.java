package de.mi.ur;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class StartActivity extends Activity {

    private Button buttonTutorial;
    private Button buttonPractice;
    private Button buttonGame;
    private Button buttonProgress;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        setupUI();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setupUI() {
        buttonTutorial = (Button) findViewById(R.id.start_tutorial_button);
        setOnClickListener(buttonTutorial);
        buttonTutorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, TutorialMainActivity.class);
                startActivity(i);
            }
        });
        buttonPractice = (Button) findViewById(R.id.start_practice_button);
        setOnClickListener(buttonPractice);
        buttonPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, PracticeMainActivity.class);
                startActivity(i);
            }
        });

        buttonGame = (Button) findViewById(R.id.start_game_button);
        buttonGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, GameMainActivity.class);
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

   /* @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Start Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://de.mi.ur/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Start Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://de.mi.ur/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/
}
