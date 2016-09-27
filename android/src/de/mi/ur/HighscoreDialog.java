package de.mi.ur;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.DataBase.NNCDatabase;

/**
 * Created by Lydia on 21.09.2016.
 */
public class HighscoreDialog extends DialogFragment {

    private EditText editText;
    private String userName;
    private boolean dialogDone = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Constants.HIGHSCORE_DIALOG_TITLE_PART_ONE + AndroidLauncher.getCurrentUserName()+Constants.HIGHSCORE_DIALOG_TITLE_PART_TWO);

        editText = new EditText(getActivity());
        builder.setView(editText);

        builder.setNegativeButton(Constants.DIALOG_NEGATIVE_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogDone = true;
            }
        });
        builder.setPositiveButton(Constants.DIALOG_POSITIVE_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = editText.getText().toString();
                AndroidLauncher.setUserName(userName);
                dialogDone = true;
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }


    public String getUserName() {
        return userName;
    }

    public boolean getDialogDone() {
        return dialogDone;
    }
}
