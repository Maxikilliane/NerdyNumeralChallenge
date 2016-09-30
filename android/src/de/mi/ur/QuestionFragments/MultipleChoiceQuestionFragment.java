package de.mi.ur.QuestionFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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


}
