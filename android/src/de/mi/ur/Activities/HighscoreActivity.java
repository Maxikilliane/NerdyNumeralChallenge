package de.mi.ur.Activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.mi.ur.DataBase.Highscore;
import de.mi.ur.DataBase.HighscoreAdapter;
import de.mi.ur.DataBase.NNCDatabase;
import de.mi.ur.R;

/**
 * Created by Anna-Marie on 01.09.2016.
 */
public class HighscoreActivity extends Activity {
    private ListView highscoreListView;
    private NNCDatabase db;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_activity);
        db = new NNCDatabase(this);
        db.open();

        setUpUI();
    }

    private void setUpUI(){
        Cursor cursor;
        highscoreListView = (ListView) findViewById(R.id.highscore_list);
        HighscoreAdapter adapter = new HighscoreAdapter(this, cursor = db.getAllHighscoresCursor() );
        highscoreListView.setAdapter(adapter);

    }

    @Override
    protected void onStop(){
        super.onStop();
        db.close();
    }
}
