package de.mi.ur.QuestionFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.mi.ur.AndroidCommunication.MultipleChoiceListener;
import de.mi.ur.R;

/**
 * Created by Anna-Marie on 02.09.2016.
 */
public class MultipleChoiceQuestionFragment extends Fragment implements View.OnClickListener{
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;


    public MultipleChoiceQuestionFragment(){}


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.free_text_question_fragment, container, false);
        setUpUI(fragmentView);
        return fragmentView;
    }

    private void setUpUI(View fragmentView){
        choice1 = (Button) fragmentView.findViewById(R.id.choice1);
        choice1.setOnClickListener(this);
        choice2 = (Button) fragmentView.findViewById(R.id.choice2);
        choice2.setOnClickListener(this);
        choice3 = (Button) fragmentView.findViewById(R.id.choice3);
        choice3.setOnClickListener(this);
        choice4 = (Button) fragmentView.findViewById(R.id.choice4);
        choice4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       // Text auf Button mit richtiger Lösung vergleichen -> irgendwo speichern
    }
}
