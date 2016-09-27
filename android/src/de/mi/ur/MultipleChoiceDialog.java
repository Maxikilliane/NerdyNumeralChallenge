package de.mi.ur;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.mi.ur.QuestionLogic.MultipleChoiceQuestion;


/**
 * Created by Lydia on 25.09.2016.
 */
public class MultipleChoiceDialog extends DialogFragment {


    private MultipleChoiceQuestion currentQuestion;
    private String[] items;
    private TextView messageTextView, questionTextView;
    private RadioGroup radioGroup;
    private boolean rightAnswer, wrongAnswer;
    private long startTime;
    private LayoutInflater inflater;
    private View dialogView;// = inflater.inflate(R.layout.multiple_choice_dialog, null);


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        startTime = System.currentTimeMillis();
        rightAnswer = false;
        wrongAnswer = false;

        inflater = getActivity().getLayoutInflater();
        currentQuestion = new MultipleChoiceQuestion(Constants.MULTIPLE_CHOICE_DIALOG_FIRST_NUMERAL_BASE, Constants.MULTIPLE_CHOICE_DIALOG_SECOND_NUMERAL_BASE, Constants.MULTIPLE_CHOICE_DIALOG_QUESTION_LENGTH);
        items = currentQuestion.generatePossAnswers();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialogView = inflater.inflate(R.layout.multiple_choice_dialog, null);
        messageTextView = (TextView) dialogView.findViewById(R.id.multiple_choice_dialog_message);
        messageTextView.setText(Constants.MULTIPLE_CHOICE_DIALOG_MESSAGE);
        questionTextView = (TextView) dialogView.findViewById(R.id.multiple_choice_dialog_question);
        questionTextView.setText(currentQuestion.getQuestion());
        radioGroup = (RadioGroup) dialogView.findViewById(R.id.multiple_choices_dialog);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton button = ((RadioButton) radioGroup.getChildAt(i));
            button.setText(items[i]);
        }
        builder.setView(dialogView);
        builder.setPositiveButton(Constants.DIALOG_POSITIVE_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkClickedAnswer();
               /* int checkedButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedButtonId == -1) {
                    //Toast.makeText(getActivity(), "You lost a life", Toast.LENGTH_SHORT).show();
                    wrongAnswer = true;
                } else {
                    RadioButton checkedButton = (RadioButton) dialogView.findViewById(checkedButtonId);
                    if (checkedButton.getText().toString().equals(currentQuestion.getRightAnswerString())) {
                        //   Toast.makeText(getActivity(), "You saved yourself", Toast.LENGTH_SHORT).show();
                        rightAnswer = true;
                    } else {
                        //   Toast.makeText(getActivity(), "You lost a life", Toast.LENGTH_SHORT).show();
                        wrongAnswer = true;
                    }
                }*/
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

    private void checkClickedAnswer (){
        int checkedButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedButtonId == -1) {
            //Toast.makeText(getActivity(), "You lost a life", Toast.LENGTH_SHORT).show();
            wrongAnswer = true;
        } else {
            RadioButton checkedButton = (RadioButton) dialogView.findViewById(checkedButtonId);
            if (checkedButton.getText().toString().equals(currentQuestion.getRightAnswerString())) {
                //   Toast.makeText(getActivity(), "You saved yourself", Toast.LENGTH_SHORT).show();
                rightAnswer = true;
            } else {
                //   Toast.makeText(getActivity(), "You lost a life", Toast.LENGTH_SHORT).show();
                wrongAnswer = true;
            }
        }
    }

    public void dismissDialog(){
        long currentTime = System.currentTimeMillis();
        long timeDifferenceInSeconds = (currentTime-startTime)/1000;
        if(timeDifferenceInSeconds >= Constants.DIALOG_SHOW_TIME_IN_SECONDS){
            checkClickedAnswer();
            dismiss();
        }
    }


    public boolean getRightAnswer() {
        return rightAnswer;
    }

    public boolean getWrongAnswer() {
        return wrongAnswer;
    }
}
