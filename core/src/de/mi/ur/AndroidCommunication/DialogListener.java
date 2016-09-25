package de.mi.ur.AndroidCommunication;

import javax.swing.text.View;

/**
 * Created by Lydia on 21.09.2016.
 */
public interface DialogListener {

    public void showHighscoreDialog();

    public boolean getDialogDone();

    public String getUserName();

    public void showMultipleChoiceDialog();

    public boolean getRightDialogAnswer();

    public boolean getWrongDialogAnswer();

}
