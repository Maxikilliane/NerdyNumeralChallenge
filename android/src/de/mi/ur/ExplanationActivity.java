package de.mi.ur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Anna-Marie on 01.08.2016.
 */
public class ExplanationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation_activity);

        Bundle extras = getIntent().getExtras();



    }
}
