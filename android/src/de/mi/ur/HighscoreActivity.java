package de.mi.ur;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import de.mi.ur.DataBase.Highscore;
import de.mi.ur.DataBase.NNCDatabase;

/**
 * Created by Anna-Marie on 01.09.2016.
 */
public class HighscoreActivity extends Activity {
    private ListView highscoreList;
    private NNCDatabase db;
    private ArrayList<Highscore> highscores;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_activity);
        db = new NNCDatabase(this);
        db.open();

        setUpUI();
    }

    private void setUpUI(){
        highscoreList = (ListView) findViewById(R.id.highscore_list);
        highscores = db.getAllHighscores();
        ArrayAdapter<Highscore> adapter = new ArrayAdapter<Highscore>(this, R.layout.highscore_listitem, highscores );
        highscoreList.setAdapter(adapter);
    }
}
