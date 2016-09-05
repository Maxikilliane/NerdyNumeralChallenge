package de.mi.ur.QuestionFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 02.09.2016.
 */
public class TrueFalseQuestionFragment extends QuestionFragment {
    private RadioGroup choices;

    private View fragmentView;

    public TrueFalseQuestionFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.true_false_fragment, container, false);
        this.fragmentView = fragmentView;
        setUpUI(fragmentView);
        return fragmentView;
    }

    private void setUpUI(View fragmentView){
        choices = (RadioGroup) fragmentView.findViewById(R.id.true_false_choices);
    }

    public boolean isCorrectAnswer(String rightAnswer){
        int checkedButtonId = choices.getCheckedRadioButtonId();
        if(checkedButtonId == -1){
            return false;
        }else{
            RadioButton checkedButton = (RadioButton)fragmentView.findViewById(checkedButtonId);
            if(checkedButton.getText().toString().equals(rightAnswer)){
                return true;
            }else{
                return false;
            }
        }
    }
}
