package de.mi.ur.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_activity);
        db = new NNCDatabase(this);
        db.open();

        setUpUI();
    }

    private void setUpUI() {
        highscoreListView = (ListView) findViewById(R.id.highscore_list);
        HighscoreAdapter adapter = new HighscoreAdapter(this, db.getAllHighscoresCursor());
        highscoreListView.setAdapter(adapter);
        adapter.bindView(highscoreListView, this, db.getAllHighscoresCursor());

       /* TextView rankTextView = (TextView) findViewById(R.id.highscore_rank_view);
        TextView pointsTextView = (TextView) findViewById(R.id.highscore_points_view);
        TextView nameTextView = (TextView) findViewById(R.id.highscore_name_view);

        int rank = cursor.getInt(cursor.getColumnIndexOrThrow("rank"));
        int points = cursor.getInt(cursor.getColumnIndexOrThrow("points"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

        rankTextView.setText(rank);
        pointsTextView.setText(points);
        nameTextView.setText(name)*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
