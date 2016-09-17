package de.mi.ur.QuestionFragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.text.InputType;
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
    private RadioGroup multipleChoices;
    private RadioButton button1;
    private TextView useless;
    private View fragmentView;


    public MultipleChoiceQuestionFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.multiple_choice_fragment, container, false);
        this.fragmentView = fragmentView;
        multipleChoices = (RadioGroup) fragmentView.findViewById(R.id.multiple_choices);
        button1 = (RadioButton) fragmentView.findViewById(R.id.choice1);
        useless = (TextView) fragmentView.findViewById(R.id.useless);

        setUpUI();
        return fragmentView;


    }

    private void setUpUI(){
        multipleChoices = (RadioGroup) fragmentView.findViewById(R.id.multiple_choices);
        useless = (TextView) fragmentView.findViewById(R.id.useless);

    }


    public void setButtonTexts(String[] texts){

        for (int i = 0; i < multipleChoices.getChildCount(); i++) {
            RadioButton button = ((RadioButton) multipleChoices.getChildAt(i));
            button.setText(texts[i]);

        }
    }

    public boolean isCorrectAnswer(String rightAnswer){
        int checkedButtonId = multipleChoices.getCheckedRadioButtonId();
        if(checkedButtonId == -1){
            return false;
        }else{
            RadioButton checkedButton = (RadioButton)fragmentView.findViewById(checkedButtonId);
            multipleChoices.clearCheck();
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
