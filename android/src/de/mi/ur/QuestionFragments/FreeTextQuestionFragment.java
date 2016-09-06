package de.mi.ur.QuestionFragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 05.08.2016.
 */
public class FreeTextQuestionFragment extends QuestionFragment {
    private EditText solutionEditText;
    private OnKeyboardListener mCallback;
    // gehört da unten mit dazu
    //private String[] numeralSystems = getResources().getStringArray(R.array.numeral_systems);

    public FreeTextQuestionFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.free_text_question_fragment, container, false);
        this.solutionEditText = (EditText) fragmentView.findViewById(R.id.freetext_edit_text);
        this.solutionEditText.setInputType(InputType.TYPE_NULL);
        return fragmentView;
    }

    public interface OnKeyboardListener{
        public void onOpen();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnKeyboardListener) context;
            this.solutionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) mCallback.onOpen();
                }
            });
            solutionEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onOpen();
                }
            });
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnKeyboardListener");
        }
    }


    public EditText getSolutionEditText(){
        return solutionEditText;
    }

    /*Komische Exceptions wenn nicht ausgegraut... Fragment FreeTextQuestionFragment{3215388} not attached to Activity
                                                              at android.app.Fragment.getResources(Fragment.java:805)
    public void setQuestionText(int numeral1Base, int numeral2Base){
        Resources res = getActivity().getResources();

        String questionText = res.getString(R.string.free_text_question_1) +numeralSystems[numeral1Base -2] +
                res.getString(R.string.free_text_question_2)+numeralSystems[numeral2Base -2] + res.getString(R.string.free_text_question_3);

        questionTextView.setText(questionText);
    }

    public void setQuestionTextTutorialQuestion(String text){
        questionTextView.setText(text);
    }
    */

    public String getTextFromSolutionEditText(){
        return solutionEditText.getText().toString();
    }

    public void deleteText(){
        solutionEditText.setText("");
    }

    public void setTextInvisible(){
        solutionEditText.setVisibility(View.INVISIBLE);
    }

    public void setTextVisible(){
        solutionEditText.setVisibility(View.VISIBLE);
    }

    public boolean isCorrectAnswer(String rightAnswer){
        if(solutionEditText.getText().toString().equals(trimLeadingZeros(rightAnswer))){
            return true;
        }else{
            return false;
        }
    }
    private String trimLeadingZeros(String string) {
        String trimmed = string.trim();
        for (int i = 0; i < string.length() - 1; i++) {
            if (string.charAt(i) == '0') {
                trimmed = trimmed.substring(1);
            } else {
                break;
            }
        }
        return trimmed;
    }

    public void setSolutionEditTextInputType(int inputType){
        solutionEditText.setInputType(inputType);
    }
}
