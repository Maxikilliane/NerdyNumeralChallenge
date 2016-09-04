package de.mi.ur.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import de.mi.ur.Constants;
import de.mi.ur.QuestionFragments.FreeTextQuestionFragment;
import de.mi.ur.QuestionLogic.FreeTextQuestion;
import de.mi.ur.QuestionLogic.MultipleChoiceQuestion;
import de.mi.ur.QuestionLogic.Question;
import de.mi.ur.QuestionLogic.TrueFalseQuestion;
import de.mi.ur.R;

/**
 * Created by Anna-Marie on 03.09.2016.
 */
public class PracticeActivity extends Activity {
    private Keyboard myKeyboard;
    private KeyboardView myKeyboardView;

    private EditText test;

    private int numeral1Base;
    private int numeral2Base;
    private int typeOfQuestion;
    private Question currentQuestion;
    private String currentQuestionText;
    private int questionLength = 2;

    private FragmentManager fragmentManager;
    private FreeTextQuestionFragment questionFragment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_activity);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            typeOfQuestion = extras.getInt(Constants.KEY_TYPE_QUESTION);
            numeral1Base = extras.getInt(Constants.KEY_NUMERAL_1_BASE);
            numeral2Base = extras.getInt(Constants.KEY_NUMERAL_2_BASE);
        }
        setUpUI();
        setUpKeyboard();
        setUpQuestion();

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
            //Do not show the preview balloons
            //myKeyboardView.setPreviewEnabled(false);
            //Key Handler
            myKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
           // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            test.setOnFocusChangeListener(new View.OnFocusChangeListener(){

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) openKeyboard(v);
                }
            });
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openKeyboard(v);
                }
            });
        }


    }

    private void setUpUI(){
        test = (EditText) findViewById(R.id.testEditText);

        test.setInputType(InputType.TYPE_NULL);
    }

    public void openKeyboard(View v){
        myKeyboardView.setVisibility(View.VISIBLE);
        myKeyboardView.setEnabled(true);
        if(v!=null){
            ((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    private void setUpQuestion(){
        switch (typeOfQuestion){
            case Constants.MULTIPLE_CHOICE:
                currentQuestion = new MultipleChoiceQuestion(numeral1Base, numeral2Base, questionLength);
                break;
            case Constants.TRUE_FALSE:
                currentQuestion = new TrueFalseQuestion(numeral1Base, numeral2Base, questionLength);
                break;
            case Constants.FREETEXT :
                currentQuestion = new FreeTextQuestion(numeral1Base, numeral2Base, questionLength);
            default:
                currentQuestion = new MultipleChoiceQuestion(numeral1Base, numeral2Base, questionLength);
        }
    }

    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {

            //von hier
            View focusCurrent = PracticeActivity.this.getWindow().getCurrentFocus();
            if(focusCurrent == null || focusCurrent.getClass()!=EditText.class){
                return;
            }
            EditText editText = (EditText) focusCurrent;
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            // bis hier: Code von Marten Pennings, in stackoverflow nicht enthalten

            switch(primaryCode){

                case 1:
                    Log.i("Key", "You just pressed 1 button");
                    // editable.insert(start, Character.toString((char) primaryCode));
                    editable.insert(start, "1");
                    break;
                case 0:
                    editable.insert(start, "0");
                    // editable.insert(start, Character.toString((char) primaryCode));
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
