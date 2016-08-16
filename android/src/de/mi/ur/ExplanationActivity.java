package de.mi.ur;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.mi.ur.QuestionFragments.FreeTextQuestionFragment;
import de.mi.ur.QuestionLogic.TutorialQuestion;

/**
 * Created by Anna-Marie on 01.08.2016.
 */
public class ExplanationActivity extends Activity implements View.OnClickListener{
    private TextView explanationTextView;
    private TextView questionTextView;
    private Button solutionButton;
    private Button continueButton;
    private Button backButton;

    private FragmentManager fragmentManager;
    private FreeTextQuestionFragment questionFragment;


    private int tutorialType;
    private int explanationNumber;
    private String [] tutorialTexts;
    private String explanationText;
    private int maxNumExplanations;

    private TutorialQuestion currentQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation_activity);
        //sorgt dafür, dass die Tastatur am Anfang nicht sofort aufpoppt
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Bundle extras = getIntent().getExtras();
        tutorialType = extras.getInt(Constants.KEY_TYPE_TUTORIAL);
        explanationNumber = extras.getInt(Constants.KEY_NUMBER_TUTORIAL);

        setUpTexts();
        setUpQuestions();
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

    private void setUpQuestions(){
        currentQuestion = new TutorialQuestion(tutorialType, explanationNumber);
    }

    /*
     * Click auf Solution-Button: grüner Hintergrund falls richtige Lösung,
     *                            roter Hintergrund falls falsche Lösung + Toast mit richtiger Lösung
     */
    private void setUpUI(){

        setUpFragment();
        explanationTextView = (TextView) findViewById(R.id.explanation_textview);
        explanationTextView.setText(Html.fromHtml(explanationText));

       /* if(currentQuestion.getNumeral1Base()!=-1){
            questionFragment.setQuestionText(currentQuestion.getNumeral1Base(), currentQuestion.getNumeral2Base());
        }else{
            questionFragment.setQuestionTextTutorialQuestion(currentQuestion.getQuestion());
        }*/
        questionTextView = (TextView) findViewById(R.id.revision_question_textview);
        questionTextView.setText(Html.fromHtml(currentQuestion.getQuestion()));

        solutionButton = (Button) findViewById(R.id.tutorial_solution_button);
        solutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String solution = questionFragment.getTextFromSolutionEditText();
                if(solution.equals(currentQuestion.getRightAnswer())){
                    questionFragment.getView().setBackgroundResource(R.color.correct_solution_green);
                }else{
                    questionFragment.getView().setBackgroundResource(R.color.wrong_solution_red);
                    showWrongAnswerToast();
                }
            }
        });

        continueButton = (Button) findViewById(R.id.explanation_continue_button);
        continueButton.setOnClickListener(this);

        backButton = (Button) findViewById(R.id.explanation_back_button);
        backButton.setOnClickListener(this);
        backButton.setEnabled(false);


    }

    private void setUpFragment(){
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FreeTextQuestionFragment questionFrag = new FreeTextQuestionFragment();
        fragmentTransaction.add(R.id.revision_answer_fragment_placeholder, questionFrag);
        fragmentTransaction.commit();

        questionFragment = questionFrag;

    }

    /*
     * Click auf Continue-Button:nächster Erklärtext wird eingeblendet
     * Click auf Back-Button: letzter Erklärtext wird eingeblendet
     *
     */
    @Override
    public void onClick(View v) {
        continueButton.setEnabled(true);
        backButton.setEnabled(true);
        switch(v.getId()){
            case R.id.explanation_continue_button:
                if(explanationNumber == maxNumExplanations-1){
                    continueButton.setEnabled(false);
                    questionFragment.getView().setBackgroundResource(R.color.black);
                }
                explanationNumber++;
                break;
            case R.id.explanation_back_button:
                if(explanationNumber==1){
                    backButton.setEnabled(false);
                }
                explanationNumber--;
                questionFragment.getView().setBackgroundResource(R.color.powder_blue);
                break;
            default:
        }
        explanationText = tutorialTexts[explanationNumber];
        explanationTextView.setText(Html.fromHtml(explanationText));
        setVisibility();

        questionTextView.setText(currentQuestion.getQuestion());
        questionFragment.deleteText();

        //sorgt dafür, dass mit Wechsel des ExplanationTexts auch der Focus vom EditText wieder weggeht
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(questionFragment.getSolutionEditText().getWindowToken(), 0);
    }

    private void setVisibility(){
        if(explanationNumber<maxNumExplanations){
            currentQuestion = new TutorialQuestion(tutorialType, explanationNumber);
            solutionButton.setVisibility(View.VISIBLE);
            solutionButton.setEnabled(true);
            questionFragment.setTextVisible();
            questionTextView.setVisibility(View.VISIBLE);
        }else{
            solutionButton.setVisibility(View.INVISIBLE);
            solutionButton.setEnabled(false);
            questionFragment.setTextInvisible();
            questionTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void showWrongAnswerToast(){
        String wrongAnswerText = getResources().getString(R.string.wrong_answer_toast_text) +" "+currentQuestion.getRightAnswer() + ".";
        Toast wrongAnswerToast = Toast.makeText(this, wrongAnswerText, Toast.LENGTH_LONG);
        wrongAnswerToast.show();

    }
}
