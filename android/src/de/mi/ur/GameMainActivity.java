package de.mi.ur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameMainActivity extends Activity  implements View.OnClickListener{

    private Button buttonStartGame;
    private Button buttonViewHighscore;
    private Button buttonHelp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main_activity);
        setupUI();


    }

    private void setupUI() {
        buttonStartGame = (Button) findViewById(R.id.game_start_button);
        buttonStartGame.setOnClickListener(this);
        buttonViewHighscore = (Button) findViewById (R.id.game_highscore_button);
        buttonViewHighscore.setOnClickListener(this);
        buttonHelp = (Button) findViewById (R.id.game_help_button);
        buttonHelp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()){
            case R.id.game_start_button:
                i = new Intent(GameMainActivity.this, AndroidLauncher.class);
                break;
            case R.id.game_highscore_button:
                break;
            case R.id.game_help_button:
                i= new Intent (GameMainActivity.this, GameHelpActivity.class);
                break;
            default:
                break;
        }
        if(i!=null){
            startActivity(i);
        }

    }
}
