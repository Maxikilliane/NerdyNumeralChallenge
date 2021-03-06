package de.mi.ur.QuestionFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Anna-Marie on 04.09.2016.
 */
public abstract class QuestionFragment extends android.support.v4.app.Fragment {
    public QuestionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public boolean isCorrectAnswer(String rightAnswer){
        return false;
    }

    //for multiple choice
    public void setButtonTexts(String[] texts){}

    //for freetextquestions
    public void setSolutionEditTextInputType(int inputType){}
}

