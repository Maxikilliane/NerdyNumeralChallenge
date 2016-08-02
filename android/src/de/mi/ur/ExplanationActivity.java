package de.mi.ur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Anna-Marie on 01.08.2016.
 */
public class ExplanationActivity extends Activity {
    private TextView explanationTextView;
    private Button continueButton;

    private int tutorialType;
    private int explanationNumber;
    private String [] tutorialTexts;
    private String explanationText;
    private int maxNumExplanations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation_activity);

        Bundle extras = getIntent().getExtras();
        tutorialType = extras.getInt(Constants.KEY_TYPE_TUTORIAL);
        explanationNumber = extras.getInt(Constants.KEY_NUMBER_TUTORIAL);

        setUpTexts();
        setUpUI();
    }

    // Welches Tutorial wurde angeklickt? Auswählen der richtigen Konstante für dieses Tutorial
    private void setUpTexts(){
        int idTexts;
        switch (tutorialType){
            case Constants.INTRO_TUTORIAL:
                idTexts = R.array.tutorial_intro;
                maxNumExplanations = Constants.MAX_EXPLANATION_NUM_INTRO;
                break;
            case Constants.DECIMAL_TUTORIAL:
                idTexts = R.array.tutorial_decimal;
                maxNumExplanations = Constants.MAX_EXPLANATION_NUM_DECIMAL;
                break;
            case Constants.OTHER_TUTORIAL:
                idTexts = R.array.tutorial_other;
                maxNumExplanations = Constants.MAX_EXPLANATION_NUM_OTHER;
                break;
            case Constants.TRICKS_TUTORIAL:
                idTexts = R.array.tutorial_tricks;
                maxNumExplanations = Constants.MAX_EXPLANATION_NUM_TRICKS;
                break;
            default:
                idTexts = -1;
                maxNumExplanations = -1;
        }
        if(idTexts != -1) {
            tutorialTexts = getResources().getStringArray(idTexts);
            this.explanationText = tutorialTexts[explanationNumber];
        }

    }

    //Bei Klick auf Continue-Button: anderer Erklärungstext (nächste Stelle im Text-Array)
    private void setUpUI(){
        explanationTextView = (TextView) findViewById(R.id.explanation_textview);
        explanationTextView.setText(explanationText);
        continueButton = (Button) findViewById(R.id.explanation_continue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(explanationNumber <=maxNumExplanations) {
                    if (explanationNumber == maxNumExplanations - 1) {
                        continueButton.setEnabled(false);
                    }
                    explanationNumber++;
                    explanationText = tutorialTexts[explanationNumber];
                    explanationTextView.setText(explanationText);
                }
            }
        });
    }
}
