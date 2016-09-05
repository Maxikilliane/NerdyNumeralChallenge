package de.mi.ur.QuestionFragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.QuestionLogic.MultipleChoiceQuestion;
import de.mi.ur.R;

/**
 * Created by Anna-Marie on 02.09.2016.
 */
public class MultipleChoiceQuestionFragment extends QuestionFragment{
    private MultipleChoiceQuestion question;

    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;

    private RadioGroup choices;
    private View thisFragmentView;


    public MultipleChoiceQuestionFragment(){
        super();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.free_text_question_fragment, container, false);
        setUpUI(fragmentView);
        thisFragmentView = fragmentView;
        return fragmentView;
    }

    private void setUpUI(View fragmentView){
        choices = (RadioGroup) fragmentView.findViewById(R.id.multiple_choices);
    }


    public void setButtonTexts(String[] texts){

        for (int i = 0; i < choices.getChildCount(); i++) {
            ((RadioButton) choices.getChildAt(i)).setText(texts[i]);

        }
    }

    public boolean isCorrectAnswer(String rightAnswer){
        int checkedButtonId = choices.getCheckedRadioButtonId();
        if(checkedButtonId == -1){
            return false;
        }else{
            RadioButton checkedButton = (RadioButton)thisFragmentView.findViewById(checkedButtonId);
            if(checkedButton.getText().toString().equals(rightAnswer)){
                return true;
            }else{
                return false;
            }
        }
    }

/*
    // Container Activity must implement this interface
    public interface OnChoiceSelectedListener {
        public void onChoiceSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            OnChoiceSelectedListener b = (OnChoiceSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnChoiceSelectedListener");
        }
    }

*/
}
