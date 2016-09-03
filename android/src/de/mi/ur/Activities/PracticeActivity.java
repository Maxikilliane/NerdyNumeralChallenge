package de.mi.ur.Activities;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 03.09.2016.
 */
public class PracticeActivity extends Activity {
    private Keyboard myKeyboard;
    private KeyboardView myKeyboardView;

    private int numeral2Base;


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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_activity);
        setUpKeyboard();
        setUpUI();
    }

    private void setUpKeyboard(){
        // später hier switch-case dazu, je nach numeral2Base bei Freitext -> anderes Keyboard
        //momentan gibts aber eh bloß das eine ;)
        myKeyboard = new Keyboard(this, R.xml.keyboard2);
        myKeyboardView = (KeyboardView) findViewById(R.id.custom_keyboard);
        myKeyboardView.setKeyboard(myKeyboard);
        //Do not show the preview balloons
        //myKeyboardView.setPreviewEnabled(false);
        //Key Handler
        myKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);

    }

    private void setUpUI(){
        EditText test = (EditText) findViewById(R.id.testEditText);
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
        test.setInputType(InputType.TYPE_NULL);
    }

    public void openKeyboard(View v){
        myKeyboardView.setVisibility(View.VISIBLE);
        myKeyboardView.setEnabled(true);
        if(v!=null){
            ((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }


}
