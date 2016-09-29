package de.mi.ur.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
 * The Tutorial-Texts and questions are displayed in this activity.
 */
public class ExplanationActivity extends AppCompatActivity implements View.OnClickListener, FreeTextQuestionFragment.OnKeyboardListener {
    private TextView explanationTextView;
    private TextView questionTextView;
    private Button solutionButton;
    private Button continueButton;
    private Button backButton;
    private Toolbar myToolbar;
    private FreeTextQuestionFragment questionFragment;

    private android.support.v4.app.FragmentManager fragmentManager;

    private int tutorialType;
    private int explanationNumber;
    private String[] tutorialTexts;
    private String explanationText;
    private int maxNumExplanations;
    private TutorialQuestion currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation_activity);
        getExtras();
        stopKeyboardPopUp();
        setUpTexts();
        setUpQuestions();
        setUpUI();
        setupToolbar();
    }

    private void getExtras() {
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            tutorialType = extras.getInt(Constants.KEY_TYPE_TUTORIAL);
            explanationNumber = extras.getInt(Constants.KEY_NUMBER_TUTORIAL);
        }
    }

    private void stopKeyboardPopUp() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }




    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.explanation_toolbar);
        setSupportActionBar(myToolbar);
        setUpToolbarTitle();
        //getSupportActionBar().setTitle(R.string.app_name);
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
                refreshTexts();
            }
        });

    }

    /*
     * Chooses the correct title for the toolbar
     */
    private void setUpToolbarTitle() {
        switch (tutorialType) {
            case Constants.INTRO_TUTORIAL:
                getSupportActionBar().setTitle(R.string.tutorial_intro_button);
                break;
            case Constants.DECIMAL_TUTORIAL:
                getSupportActionBar().setTitle(Html.fromHtml(getResources().getString(R.string.tutorial_from_10_button)));
                break;
            case Constants.OTHER_TUTORIAL:
                getSupportActionBar().setTitle(Html.fromHtml(getResources().getString(R.string.tutorial_from_other_button)));
                break;
            case Constants.TRICKS_TUTORIAL:
                getSupportActionBar().setTitle(R.string.tutorial_tricks_button);
                break;
            default:
                getSupportActionBar().setTitle(R.string.tutorial_toolbar_headline);
        }
    }

    /*
     * changes the explanation texts and gets the focus away from them
     * deletes possible answers to the revision questions
     */
    private void refreshTexts(){
        explanationText = tutorialTexts[explanationNumber];
        explanationTextView.setText(Html.fromHtml(explanationText));
        setVisibility();

        questionTextView.setText(currentQuestion.getQuestion());
        questionFragment.deleteText();

        //sorgt dafür, dass mit Wechsel des ExplanationTexts auch der Focus vom EditText wieder weggeht
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(questionFragment.getSolutionEditText().getWindowToken(), 0);
    }

    /*
     * Saving which tutorial has been chosen and necessary information
     * maxNumExplanations will determine when the -> button will be deactivated...
     */
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
     * Sets up UI
     * Click on Solution-Button:    green background if solution is correct
     *                              red background + Toast with correct solution, if solution is incorrect
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
     * Click on Continue-Button:next explanation text is shown
     * Click on Back-Button: last explanation-text is shown
     * UI-changes are caught (color of the answer-background, (in)visibility of the question-parts
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

        refreshTexts();
    }

    /*
     * Handles special cases at the end or beginning of a tutorial
     * beginning: back button diasabled
     * end: next button disabled, questionFragment is invisible (no question on the last part of a tutorial)
     */
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

    /*
     * Notifys the user of a wrong answer and gives the right answer
     */
    private void showWrongAnswerToast(){
        String wrongAnswerText = getResources().getString(R.string.wrong_answer_toast_text) +" "+currentQuestion.getRightAnswer() + ".";
        Toast wrongAnswerToast = Toast.makeText(this, wrongAnswerText, Toast.LENGTH_LONG);
        wrongAnswerToast.show();

    }

    @Override
    public void onOpen() {

    }

    /*
     * This method shows the menu (only next arrow here) in the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_explanation_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*
     * This method is the onClickListener and onClick-Method for the menu-item (next arrow)
     */
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
        refreshTexts();
        return super.onOptionsItemSelected(item);
    }
}
