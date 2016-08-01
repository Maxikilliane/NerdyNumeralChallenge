package de.mi.ur;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Anna-Marie on 01.08.2016.
 */
public class TutorialMainActivity extends Activity implements View.OnClickListener {
    //ich weiß nicht, ob man die Buttons hier überhaupt braucht. ;) Bin aber jetzt mal vorsichtig
    Button introButton = (Button)findViewById(R.id.tutorial_intro_button);
    Button fromDecimalButton = (Button) findViewById(R.id.tutorial_from_decimal_button);
    Button fromOtherButton = (Button)findViewById(R.id.tutorial_from_other_numeral_button);
    Button tricksButton= (Button) findViewById(R.id.tutorial_tricks_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_main_activity);


    }

    //noch nicht implementiert... Da muss ich vermutlich irgendwie ein Bundle mitgeben, dass die ExplanationActivity weiß,
    //welches Tutorial sie anzeigen soll.
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tutorial_intro_button:
                break;
            case R.id.tutorial_from_decimal_button:
                break;
            case R.id.tutorial_from_other_numeral_button:
                break;
            case R.id.tutorial_tricks_button:
                break;
            default:
                break;
        }
    }
}
