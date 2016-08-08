package de.mi.ur.QuestionFragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 05.08.2016.
 */
public class FreeTextQuestionFragment extends Fragment {
    private EditText solutionEditText;

    public FreeTextQuestionFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.free_text_question_fragment, container, false);
        solutionEditText = (EditText) fragmentView.findViewById(R.id.freetext_edit_text);
        return fragmentView;
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
}
