package de.mi.ur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Anna-Marie on 01.08.2016.
 */
public class TutorialMainActivity extends Activity implements View.OnClickListener {
    //ich weiß nicht, ob man die Buttons hier überhaupt braucht. ;) Bin aber jetzt mal vorsichtig
    private Button introButton;
    private Button fromDecimalButton;
    private Button fromOtherButton;
    private Button tricksButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_main_activity);
        setUpUI();
    }

    private void setUpUI(){
        introButton = (Button)findViewById(R.id.tutorial_intro_button);
        introButton.setOnClickListener(this);
        fromDecimalButton = (Button) findViewById(R.id.tutorial_from_decimal_button);
        fromDecimalButton.setOnClickListener(this);
        fromOtherButton = (Button)findViewById(R.id.tutorial_from_other_numeral_button);
        fromOtherButton.setOnClickListener(this);
        tricksButton= (Button) findViewById(R.id.tutorial_tricks_button);
        tricksButton.setOnClickListener(this);
    }

    //noch nicht implementiert... Da muss ich vermutlich irgendwie ein Bundle mitgeben, dass die ExplanationActivity weiß,
    //welches Tutorial sie anzeigen soll.
    @Override
    public void onClick(View v) {
        Intent i = new Intent (TutorialMainActivity.this, ExplanationActivity.class);
        switch(v.getId()){
            case R.id.tutorial_intro_button:
                i.putExtra(Constants.KEY_TUTORIAL, Constants.INTRO_TUTORIAL);
                break;
            case R.id.tutorial_from_decimal_button:
                i.putExtra(Constants.KEY_TUTORIAL, Constants.DECIMAL_TUTORIAL);
                break;
            case R.id.tutorial_from_other_numeral_button:
                i.putExtra(Constants.KEY_TUTORIAL, Constants.OTHER_TUTORIAL);
                break;
            case R.id.tutorial_tricks_button:
                i.putExtra(Constants.KEY_TUTORIAL, Constants.TRICKS_TUTORIAL);
                break;
            default:
                break;
        }

        if(i.getExtras()!= null){
            startActivity(i);
        }
    }
}
