package de.mi.ur;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import de.mi.ur.Activities.SettingsActivity;
import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.DataBase.NNCDatabase;

/**
 * Created by Lydia on 21.09.2016.
 */
public class HighscoreDialog extends DialogFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private EditText editText;
    private String userName;
    private boolean dialogDone = false;
    private SharedPreferences sharedPref;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        loadPreferences();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Constants.HIGHSCORE_DIALOG_TITLE_PART_ONE + userName+Constants.HIGHSCORE_DIALOG_TITLE_PART_TWO);

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
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SettingsActivity.KEY_PREF_USER_NAME, userName);
                editor.commit();
                //AndroidLauncher.setUserName(userName);
                dialogDone = true;
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

    private void loadPreferences(){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userName = sharedPref.getString(SettingsActivity.KEY_PREF_USER_NAME, "");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadPreferences();
    }

    public String getUserName() {
        return userName;
    }

    public boolean getDialogDone() {
        return dialogDone;
    }
}
