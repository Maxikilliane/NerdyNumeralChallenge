package de.mi.ur.QuestionFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 05.08.2016.
 */
public class FreeTextQuestionFragment extends QuestionFragment {
    private EditText solutionEditText;
    private OnKeyboardListener mCallback;

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
        this.solutionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) mCallback.onOpen();
            }
        });
        this.solutionEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onOpen();
            }
        });
        return fragmentView;
    }

    public interface OnKeyboardListener{
         void onOpen();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnKeyboardListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnKeyboardListener");
        }
    }



    public EditText getSolutionEditText(){
        return solutionEditText;
    }

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

    /*
     * Gets the input from the EditText and compares it in case of right answer returns true, else false,
     * in both cases resets EditText to empty
     */
    public boolean isCorrectAnswer(String rightAnswer){
        if((solutionEditText.getText().toString()).equals(trimLeadingZeros(rightAnswer))){
            solutionEditText.setText("");
            return true;
        }else{
            solutionEditText.setText("");
            return false;
        }
    }

    /*
     * Value of a number is the same with or without leading zeros, for comparability, they are trimmed away
     */
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
