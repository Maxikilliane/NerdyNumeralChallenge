package de.mi.ur.Activities;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import de.mi.ur.Constants;
import de.mi.ur.DataBase.NNCDatabase;
import de.mi.ur.LevelLogic.DifficultyCalculator;
import de.mi.ur.LevelLogic.Level;
import de.mi.ur.QuestionFragments.FreeTextQuestionFragment;
import de.mi.ur.QuestionFragments.MultipleChoiceQuestionFragment;
import de.mi.ur.QuestionFragments.QuestionFragment;
import de.mi.ur.QuestionFragments.TrueFalseQuestionFragment;
import de.mi.ur.QuestionLogic.FreeTextQuestion;
import de.mi.ur.QuestionLogic.MultipleChoiceQuestion;
import de.mi.ur.QuestionLogic.Question;
import de.mi.ur.QuestionLogic.TrueFalseQuestion;
import de.mi.ur.R;

/**
 * Created by Anna-Marie on 03.09.2016.
 */
public class PracticeActivity extends AppCompatActivity implements FreeTextQuestionFragment.OnKeyboardListener{
    private TextView questionTextView, questionChangeableView;
    private ProgressBar practiseProgressBar;
    private Button solutionButton;

    private android.support.v4.app.FragmentManager fragmentManager;
    private QuestionFragment questionFragment;

    private Keyboard myKeyboard;
    private KeyboardView myKeyboardView;
    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener;

    private int numeral1Base, numeral2Base;
    private int questionLength;

    private int typeOfQuestion;
    private Question currentQuestion;
    private String questionTypeText;
    private boolean currentQuestionSolved = false;

    private String[] numeralSystems;
    private String [] buttonTexts;

    private NNCDatabase db;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_activity);
        init();
        setUpUI();
        setUpKeyboardHandler();
        setUpKeyboard();
        setUpQuestionTypeSpecificStuff();

    }

    protected void onStart(){
        super.onStart();
        switch (typeOfQuestion){
            case Constants.MULTIPLE_CHOICE:
                questionFragment.setButtonTexts(buttonTexts);
                break;
            case Constants.FREETEXT:
                questionFragment.setSolutionEditTextInputType(InputType.TYPE_NULL);
                break;
        }
    }

    private void init(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            questionLength = extras.getInt(Constants.KEY_QUESTION_LENGTH);
            typeOfQuestion = extras.getInt(Constants.KEY_TYPE_QUESTION);
            numeral1Base = extras.getInt(Constants.KEY_NUMERAL_1_BASE);
            numeral2Base = extras.getInt(Constants.KEY_NUMERAL_2_BASE);
        }
        numeralSystems = getResources().getStringArray(R.array.numeral_systems);

        db = new NNCDatabase(this);

    }

    private void setUpUI(){
        questionTextView = (TextView) findViewById(R.id.question_textview);
        questionChangeableView = (TextView) findViewById(R.id.question_changeable_textview);
        practiseProgressBar = (ProgressBar) findViewById(R.id.question_progressbar);
        solutionButton = (Button) findViewById(R.id.question_solution_button);
        solutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                if(questionFragment.isCorrectAnswer(currentQuestion.getRightAnswerString())){
                    text = "correct";
                    currentQuestionSolved = true;
                }else{
                    text ="wrong";
                    currentQuestionSolved = false;
                }
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                updateProgress();
            }
        });
    }

    private void updateProgress(){
        if(currentQuestionSolved){
            practiseProgressBar.incrementProgressBy(100 / Constants.NUM_QUESTIONS_PER_PRACTICE);
            currentQuestionSolved = false;

            if(practiseProgressBar.getProgress() == 100 ){
                savePointsToDatabase();
                // an dieser Stelle müssten dann noch die geschafften Aufgaben (Punkte) in die Datenbank gespeichert werden
                finish();
            }
        }
        updateQuestion();
    }

    private void savePointsToDatabase(){
        db.open();
        Level currentLevel = db.getCurrentLevel();
        int currentPoints = currentLevel.getPointsNeededForThisLevel();
        int pointsToAdd = DifficultyCalculator.getPointsPerQuestion(typeOfQuestion, numeral1Base, numeral2Base) * Constants.NUM_QUESTIONS_PER_PRACTICE;
        db.insertCurrentLevelPoints(currentPoints + pointsToAdd);
        if (db.checkIfNextLevel()){
            Toast.makeText(this, "next Level", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(PracticeActivity.this, ProgressActivity.class));
        }
        db.close();
    }


    private void updateQuestion(){
        setUpQuestion();
        questionChangeableView.setText(currentQuestion.getQuestion());

    }

    private void setUpKeyboardHandler(){
        mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
            }

            @Override
            public void onRelease(int primaryCode) {
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                View focusCurrent = PracticeActivity.this.getWindow().getCurrentFocus();
                if(focusCurrent ==null || focusCurrent.getClass()!= android.support.v7.widget.AppCompatEditText.class){
                    return;
                }
                EditText editText = (EditText) focusCurrent;
                Editable editable = editText.getText();
                int start = editText.getSelectionStart();
                switch(primaryCode){
                    case -1:
                        if(editable != null && start >0){
                            editable.delete(start -1, start);
                        }
                        break;
                    case 500000:
                        hideCustomKeyboard();
                        break;
                    default:
                        editable.insert(start, Character.toString((char) primaryCode));
                }
            }

            @Override
            public void onText(CharSequence text) {
            }

            @Override
            public void swipeLeft() {
            }

            @Override
            public void swipeRight() {
            }

            @Override
            public void swipeDown() {
            }

            @Override
            public void swipeUp() {
            }
        };
    }

    private void setUpKeyboard(){
        if(typeOfQuestion == Constants.FREETEXT){
            switch (numeral2Base){
                case 2:myKeyboard = new Keyboard(this, R.xml.keyboard2);
                    break;
                case 3:myKeyboard = new Keyboard(this, R.xml.keyboard3);
                    break;
                case 4: myKeyboard = new Keyboard(this, R.xml.keyboard4);
                    break;
                case 5: myKeyboard = new Keyboard(this, R.xml.keyboard5);
                    break;
                case 6: myKeyboard = new Keyboard(this, R.xml.keyboard6);
                    break;
                case 7: myKeyboard = new Keyboard(this, R.xml.keyboard7);
                    break;
                case 8: myKeyboard = new Keyboard(this, R.xml.keyboard8);
                    break;
                case 9: myKeyboard = new Keyboard(this, R.xml.keyboard9);
                    break;
                case 10: myKeyboard = new Keyboard(this, R.xml.keyboard10);
                    break;
                case 11: myKeyboard = new Keyboard(this, R.xml.keyboard11);
                    break;
                case 12: myKeyboard = new Keyboard(this, R.xml.keyboard12);
                    break;
                case 13: myKeyboard = new Keyboard(this, R.xml.keyboard13);
                    break;
                case 14: myKeyboard = new Keyboard(this, R.xml.keyboard14);
                    break;
                case 15: myKeyboard = new Keyboard(this, R.xml.keyboard15);
                    break;
                case 16: myKeyboard = new Keyboard(this, R.xml.keyboard16);
                    break;
                default: myKeyboard = new Keyboard(this, R.xml.keyboard16);
            }
            myKeyboardView = (KeyboardView) findViewById(R.id.custom_keyboard);
            myKeyboardView.setKeyboard(myKeyboard);
            //preview ballons not shown
            myKeyboardView.setPreviewEnabled(false);
            myKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }


    private void setUpQuestionTypeSpecificStuff(){
        Resources StringRes = getResources();
        switch (typeOfQuestion){
            case Constants.MULTIPLE_CHOICE:
                this.questionFragment = new MultipleChoiceQuestionFragment();
                currentQuestion = new MultipleChoiceQuestion(numeral1Base, numeral2Base, questionLength);
                buttonTexts = currentQuestion.generatePossAnswers();
                this.questionTypeText = StringRes.getString(R.string.multiple_choice_question_1)+numeralSystems[numeral1Base-2]+StringRes.getString(R.string.multiple_choice_question_2)+numeralSystems[numeral2Base-2] + StringRes.getString(R.string.multiple_choice_question_3);
                break;
            case Constants.TRUE_FALSE:
                this.questionFragment = new TrueFalseQuestionFragment();
                this.currentQuestion = new TrueFalseQuestion(numeral1Base, numeral2Base, questionLength);
                this.questionTypeText = StringRes.getString(R.string.true_false_question_1);
                break;
            case Constants.FREETEXT :
                this.questionFragment = new FreeTextQuestionFragment();
                this.currentQuestion = new FreeTextQuestion(numeral1Base, numeral2Base, questionLength);
                this.questionTypeText = StringRes.getString(R.string.freetext_question_1)+numeralSystems[numeral1Base-2]+StringRes.getString(R.string.freetext_question_2)+numeralSystems[numeral2Base-2] + StringRes.getString(R.string.freetext_question_3)+" ";
        }

        setUpFragment(questionFragment);
        questionTextView.setText(questionTypeText);
        questionChangeableView.setText(currentQuestion.getQuestion());
    }

    private void setUpQuestion(){
        switch (typeOfQuestion){
            case Constants.MULTIPLE_CHOICE:
                currentQuestion = new MultipleChoiceQuestion(numeral1Base, numeral2Base, questionLength);
                buttonTexts = currentQuestion.generatePossAnswers();
                questionFragment.setButtonTexts(buttonTexts);
                break;
            case Constants.TRUE_FALSE:
                currentQuestion = new TrueFalseQuestion(numeral1Base,numeral2Base, questionLength);
                break;
            default:
                currentQuestion = new FreeTextQuestion(numeral1Base, numeral2Base, questionLength);
        }
    }

    private void setUpFragment(QuestionFragment questionFragment){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.question_fragment_placeholder, questionFragment);
        fragmentTransaction.commit();
        this.questionFragment = questionFragment;
    }

    public void openKeyboard(View v){
        myKeyboardView.setVisibility(View.VISIBLE);
        myKeyboardView.setEnabled(true);
        if(v!=null){
            ((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    public void hideCustomKeyboard() {
        myKeyboardView.setVisibility(View.GONE);
        myKeyboardView.setEnabled(false);
    }

    @Override
    public void onOpen() {
        openKeyboard(findViewById(android.R.id.content));
    }
}
