package de.mi.ur.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 31.08.2016.
 */
public class ProgressActivity extends AppCompatActivity {
    private Toolbar myToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_activity);
        setupToolbar();
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

}
