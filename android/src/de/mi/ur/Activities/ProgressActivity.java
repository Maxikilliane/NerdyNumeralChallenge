package de.mi.ur.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.mi.ur.DataBase.NNCDatabase;
import de.mi.ur.LevelLogic.Level;
import de.mi.ur.R;

/**
 * Created by Anna-Marie on 31.08.2016.
 */
public class ProgressActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private TextView levelNumberView;
    private TextView levelNameView;
    private ProgressBar levelProgressBar;
    private Level currentLevel;
    private NNCDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_activity);
        setupToolbar();
        setUpUI();
        setUpProgress();
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.progress_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.progress_toolbar_headline);
        myToolbar.setNavigationIcon(R.drawable.toolbar_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpUI() {
        levelNumberView = (TextView) findViewById(R.id.level_number);
        levelNameView = (TextView) findViewById(R.id.level_name);
        levelProgressBar = (ProgressBar) findViewById(R.id.level_progress_bar);
        db = new NNCDatabase(this);
        db.open();
        currentLevel = db.getCurrentLevel();
        String levelNumberText = getResources().getString(R.string.level) + currentLevel.getLevelNum();
        levelNumberView.setText(levelNumberText);
        levelNameView.setText(currentLevel.getLevelName());
        db.close();
    }

    private void setUpProgress() {
        db.open();
        int currentLevelNum = currentLevel.getLevelNum();
        if (currentLevelNum >= 9) {
            levelProgressBar.setProgress(100);
        } else {
            int start = db.getLevel(currentLevel.getLevelNum()).getPointsNeededForThisLevel();
            int end = db.getLevel(currentLevel.getLevelNum() + 1).getPointsNeededForThisLevel();
            int percentage100 = end - start;
            int currentPoints = currentLevel.getPointsNeededForThisLevel();
            double currentPercentage = (((double) (currentPoints - start) / percentage100));
            int currentProgress = (int) (currentPercentage * 100);
            levelProgressBar.setProgress(currentProgress);
        }
        db.close();


    }
}
