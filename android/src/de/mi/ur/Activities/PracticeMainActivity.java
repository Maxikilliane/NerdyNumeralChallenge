package de.mi.ur.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import de.mi.ur.Constants;
import de.mi.ur.R;

/**
 * Created by Lydia on 15.08.2016.
 */
public class PracticeMainActivity extends AppCompatActivity implements View.OnClickListener {
    private NumberPicker firstNumberSystem;
    private NumberPicker secondNumberSystem;

    private Button multipleChoice;
    private Button trueFalse;
    private Button freeText;
    private Toolbar myToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_main_activity);
        setupToolbar();
        setupNumberPickers();
        setupUI();
    }

    private void setupToolbar() {
        //myToolbar = (Toolbar) findViewById(R.id.practice_main_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.practice_main_toolbar_headline);
        // myToolbar.setNavigationIcon(R.drawable.toolbar_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupUI() {
       /* multipleChoice = (Button) findViewById(R.id.multipleChoiceButton);
        multipleChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.multiple_choice_fragment);
            }
        });

        trueFalse = (Button) findViewById(R.id.wrongTrueButton);
        trueFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.true_false_fragment);
            }
        });

        freeText = (Button) findViewById(R.id.manualEntryButton);
        freeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setContentView(R.layout.free_text_question_fragment);
            }
        });
        */
        multipleChoice = (Button) findViewById(R.id.multiple_choice_button);
        multipleChoice.setOnClickListener(this);
        freeText = (Button) findViewById(R.id.manual_entry_button);
        freeText.setOnClickListener(this);
        trueFalse = (Button) findViewById(R.id.wrong_true_button);
        trueFalse.setOnClickListener(this);
    }

    private void setupNumberPickers() {
        firstNumberSystem = (NumberPicker) findViewById(R.id.firstNumberPicker);
        secondNumberSystem = (NumberPicker) findViewById(R.id.secondNumberPicker);
        firstNumberSystem.setMinValue(2);
        firstNumberSystem.setMaxValue(16);
        secondNumberSystem.setMinValue(2);
        secondNumberSystem.setMaxValue(16);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.multiple_choice_button:
                i = new Intent(PracticeMainActivity.this, PracticeActivity.class);
                i.putExtra(Constants.KEY_TYPE_QUESTION, Constants.MULTIPLE_CHOICE);
                break;
            case R.id.wrong_true_button:
                i = new Intent(PracticeMainActivity.this, PracticeActivity.class);
                i.putExtra(Constants.KEY_TYPE_QUESTION, Constants.TRUE_FALSE);
                break;
            case R.id.manual_entry_button:
                i = new Intent(PracticeMainActivity.this, PracticeActivity.class);
                i.putExtra(Constants.KEY_TYPE_QUESTION, Constants.FREETEXT);
                break;
        }

        if (i != null) {
            int numeral1Base = firstNumberSystem.getValue();
            int numeral2Base = secondNumberSystem.getValue();
            i.putExtra(Constants.KEY_NUMERAL_1_BASE, numeral1Base);
            i.putExtra(Constants.KEY_NUMERAL_2_BASE, numeral2Base);
            startActivity(i);
        }
    }
}
