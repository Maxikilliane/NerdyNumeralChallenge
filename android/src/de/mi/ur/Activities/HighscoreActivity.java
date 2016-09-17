package de.mi.ur.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import de.mi.ur.DataBase.HighscoreAdapter;
import de.mi.ur.DataBase.NNCDatabase;
import de.mi.ur.R;

/**
 * Created by Anna-Marie on 01.09.2016.
 * Evtl CursorLoader verwenden um den Main-Thread nicht zu blockieren!
 */
public class HighscoreActivity extends AppCompatActivity {
    private ListView highscoreListView;
    private NNCDatabase db;

    private Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_activity);
        db = new NNCDatabase(this);
        db.open();

        setUpUI();
        setupToolbar();
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.game_highscore_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.game_highscore_toolbar_headline);
        myToolbar.setNavigationIcon(R.drawable.toolbar_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpUI() {
        highscoreListView = (ListView) findViewById(R.id.highscore_list);
        HighscoreAdapter adapter = new HighscoreAdapter(this, db.getAllHighscoresCursor());
        highscoreListView.setAdapter(adapter);
        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.highscore_listitem, null);
        highscoreListView.addHeaderView(v);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }


    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
