package de.mi.ur;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import de.mi.ur.AndroidCommunication.DialogListener;

/**
 * Created by Lydia on 21.09.2016.
 */
public class HighscoreDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do you like to enter a new username?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "No Text is entered", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Name is entered", Toast.LENGTH_SHORT).show();
            }
        });
        Dialog dialog = builder.create();


        return dialog;
    }


   /* public void showDialog() {
        HighscoreDialog highscoreDialog = new HighscoreDialog();
        highscoreDialog.show(getFragmentManager(), "My HighscoreDialog");
    }*/

}
