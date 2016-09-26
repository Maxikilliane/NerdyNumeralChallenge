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

    private HighscoreListener highscoreListener;
    private NNCDatabase nncDatabase;

    private String userName;
    private boolean dialogDone = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Would you like to enter a new username?");

        editText = new EditText(getActivity());
        builder.setView(editText);

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "No Text is entered", Toast.LENGTH_SHORT).show();
                dialogDone = true;
                // highscoreListener.saveHighscoreToDatabase(nncDatabase.checkIfNewHighscore(points),points,userName);
            }
        });
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Name is entered", Toast.LENGTH_SHORT).show();
                userName = editText.getText().toString();
                System.out.println("username is entered");
                dialogDone = true;
                // highscoreListener.saveHighscoreToDatabase(nncDatabase.checkIfNewHighscore(points),points,userName);
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


   /* public void showDialog() {
        HighscoreDialog highscoreDialog = new HighscoreDialog();
        highscoreDialog.show(getFragmentManager(), "My HighscoreDialog");
    }*/

}
