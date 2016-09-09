package de.mi.ur.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.mi.ur.Constants;
import de.mi.ur.QuestionFragments.FreeTextQuestionFragment;
import de.mi.ur.QuestionLogic.TutorialQuestion;
import de.mi.ur.R;

/**
 * Created by Anna-Marie on 01.08.2016.
 *
 *
 */
public class ExplanationActivity extends AppCompatActivity implements View.OnClickListener, FreeTextQuestionFragment.OnKeyboardListener {
    private TextView explanationTextView;
    private TextView questionTextView;
    private Button solutionButton;
    private Button continueButton;
    private Button backButton;

    private android.support.v4.app.FragmentManager fragmentManager;
    private FreeTextQuestionFragment questionFragment;


    private int tutorialType;
    private int explanationNumber;
    private String[] tutorialTexts;
    private String explanationText;
    private int maxNumExplanations;

    private TutorialQuestion currentQuestion;

    private Toolbar myToolbar;


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
        setupToolbar();

    }


    // durch diese Methode wird das Menü (hier nur der Next Pfeil) in der Toolbar angezeigt
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_explanation_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //diese Methode ist der "OnClickListener" und die "onClick-Methode" für das MenuItem, also den nextPfeil
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        continueButton.setEnabled(true);
        backButton.setEnabled(true);

        if (explanationNumber == maxNumExplanations - 1) {
            continueButton.setEnabled(false);
            questionFragment.getView().setBackgroundResource(R.color.black);
            explanationNumber++;
        } else if (explanationNumber != maxNumExplanations) {
            questionFragment.getView().setBackgroundResource(R.drawable.mybackground);
            explanationNumber++;
        } else {
            continueButton.setEnabled(false);
        }
        explanationText = tutorialTexts[explanationNumber];
        explanationTextView.setText(Html.fromHtml(explanationText));
        setVisibility();

        questionTextView.setText(currentQuestion.getQuestion());
        questionFragment.deleteText();

        //sorgt dafür, dass mit Wechsel des ExplanationTexts auch der Focus vom EditText wieder weggeht
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(questionFragment.getSolutionEditText().getWindowToken(), 0);
        //Toast.makeText(ExplanationActivity.this, "yes", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.explanation_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        myToolbar.setNavigationIcon(R.drawable.toolbar_back);
       /* if( explanationNumber < maxNumExplanations-1){
            myToolbar.setNavigationIcon(R.drawable.toolbar_next);
        }*/
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (explanationNumber == 0) {
                    backButton.setEnabled(false);
                    finish();
                } else if (explanationNumber == 1) {
                    backButton.setEnabled(false);
                    explanationNumber--;
                } else {
                    backButton.setEnabled(true);
                    explanationNumber--;
                }

                continueButton.setEnabled(true);




                explanationText = tutorialTexts[explanationNumber];
                explanationTextView.setText(Html.fromHtml(explanationText));
                setVisibility();

                questionTextView.setText(currentQuestion.getQuestion());
                questionFragment.deleteText();

                //sorgt dafür, dass mit Wechsel des ExplanationTexts auch der Focus vom EditText wieder weggeht
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(questionFragment.getSolutionEditText().getWindowToken(), 0);


            }
        });

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
        if (idTexts != -1) {
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

        questionTextView = (TextView) findViewById(R.id.revision_question_textview);
        questionTextView.setText(Html.fromHtml(currentQuestion.getQuestion()));

        solutionButton = (Button) findViewById(R.id.tutorial_solution_button);
        solutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String solution = questionFragment.getTextFromSolutionEditText();
                if (currentQuestion.isCorrectAnswer(solution)) {
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
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
                } else {
                    questionFragment.getView().setBackgroundResource(R.drawable.mybackground);
                }
                explanationNumber++;
                break;
            case R.id.explanation_back_button:
                if (explanationNumber == 1) {
                    backButton.setEnabled(false);
                }
                explanationNumber--;
                questionFragment.getView().setBackgroundResource(R.drawable.mybackground);
                break;
            default:
        }
        explanationText = tutorialTexts[explanationNumber];
        explanationTextView.setText(Html.fromHtml(explanationText));
        setVisibility();

        questionTextView.setText(currentQuestion.getQuestion());
        questionFragment.deleteText();

        //sorgt dafür, dass mit Wechsel des ExplanationTexts auch der Focus vom EditText wieder weggeht
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(questionFragment.getSolutionEditText().getWindowToken(), 0);
    }

    private void setVisibility() {
        if (explanationNumber < maxNumExplanations) {
            currentQuestion = new TutorialQuestion(tutorialType, explanationNumber);
            solutionButton.setVisibility(View.VISIBLE);
            solutionButton.setEnabled(true);
            questionFragment.setTextVisible();
            questionTextView.setVisibility(View.VISIBLE);
        } else {
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

    @Override
    public void onOpen() {

    }
}
