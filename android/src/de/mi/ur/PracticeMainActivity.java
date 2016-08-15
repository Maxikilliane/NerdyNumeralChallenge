package de.mi.ur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * Created by Lydia on 15.08.2016.
 */
public class PracticeMainActivity extends Activity implements View.OnClickListener {

    private NumberPicker firstNumberSystem;
    private NumberPicker secondNumberSystem;

    private Button multipleChoice;
    private Button trueFalse;
    private Button freeText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_main_activity);
        setUpNumberPickers();
        setupUI();
    }

    private void setupUI() {
        multipleChoice = (Button) findViewById(R.id.multipleChoiceButton);
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
    }

    private void setUpNumberPickers() {
        firstNumberSystem = (NumberPicker) findViewById(R.id.firstNumberPicker);
        secondNumberSystem = (NumberPicker) findViewById(R.id.secondNumberPicker);
        firstNumberSystem.setMinValue(2);
        firstNumberSystem.setMaxValue(16);
        secondNumberSystem.setMinValue(2);
        secondNumberSystem.setMaxValue(16);
    }

    @Override
    public void onClick(View v) {

    }
}
