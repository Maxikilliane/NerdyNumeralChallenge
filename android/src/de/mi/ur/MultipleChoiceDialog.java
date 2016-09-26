package de.mi.ur;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
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
    private TextView textView1, textView2;
    private RadioGroup radioGroup;
    private boolean rightAnswer, wrongAnswer;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        rightAnswer = false;
        wrongAnswer = false;

        LayoutInflater inflater = getActivity().getLayoutInflater();
        currentQuestion = new MultipleChoiceQuestion(2, 10, 6);
        items = currentQuestion.generatePossAnswers();
        System.out.println("Antworten" + items[0] + "a" + items[1]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Rette dein Leben indem du für die gegebene Zahl im Binärsystem die richtige Entsprechung im Dezimalsystem auswählst.");

        final View dialogView = inflater.inflate(R.layout.multiple_choice_dialog, null);
        textView1 = (TextView) dialogView.findViewById(R.id.multiple_choice_dialog_message);
        textView1.setText("Rette dein Leben indem du für die gegebene Zahl im Binärsystem die richtige Entsprechung im Dezimalsystem auswählst.");
        textView2 = (TextView) dialogView.findViewById(R.id.multiple_choice_dialog_question);
        textView2.setText(currentQuestion.getQuestion());
        radioGroup = (RadioGroup) dialogView.findViewById(R.id.multiple_choices_dialog);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton button = ((RadioButton) radioGroup.getChildAt(i));
            button.setText(items[i]);
        }
        builder.setView(dialogView);

       /* builder.setNegativeButton("Die", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "You lost a life", Toast.LENGTH_SHORT).show();
               wrongAnswer = true;
                System.out.println("Der MultipleChoiceDialog ist beendet");
            }
        });*/
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int checkedButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedButtonId == -1) {
                    Toast.makeText(getActivity(), "You lost a life", Toast.LENGTH_SHORT).show();
                    wrongAnswer = true;
                } else {
                    RadioButton checkedButton = (RadioButton) dialogView.findViewById(checkedButtonId);
                    if (checkedButton.getText().toString().equals(currentQuestion.getRightAnswerString())) {
                        Toast.makeText(getActivity(), "You saved yourself", Toast.LENGTH_SHORT).show();
                        rightAnswer = true;
                    } else {
                        Toast.makeText(getActivity(), "You lost a life", Toast.LENGTH_SHORT).show();
                        wrongAnswer = true;
                    }
                }
                System.out.println("Der MultipleChoiceDialog ist beendet");
            }
        });

        Dialog dialog = builder.create();
        return dialog;
    }


    public boolean getRightAnswer() {
        return rightAnswer;
    }

    public boolean getWrongAnswer() {
        return wrongAnswer;
    }
}
