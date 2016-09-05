package de.mi.ur.QuestionFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mi.ur.R;

/**
 * Created by Anna-Marie on 02.09.2016.
 */
public class TrueFalseQuestionFragment extends QuestionFragment {
    public TrueFalseQuestionFragment() {
        super();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.true_false_fragment, container, false);
        setUpUI(fragmentView);
        return fragmentView;
    }

    private void setUpUI(View fragmentView) {

    }
}
