package de.mi.ur.QuestionFragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 05.08.2016.
 */
public class FreeTextQuestionFragment extends Fragment {
    private TextView questionTextView;
    private EditText solutionEditText;
    // gehört da unten mit dazu
    //private String[] numeralSystems = getResources().getStringArray(R.array.numeral_systems);

    public FreeTextQuestionFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.free_text_question_fragment, container, false);
        solutionEditText = (EditText) fragmentView.findViewById(R.id.freetext_edit_text);
        questionTextView = (TextView) fragmentView.findViewById(R.id.freetext_question_textview);
        return fragmentView;
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
}
