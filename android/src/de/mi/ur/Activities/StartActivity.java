package de.mi.ur.Activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.GregorianCalendar;

import de.mi.ur.AlertReceiver;
import de.mi.ur.DataBase.NNCDatabase;
import de.mi.ur.R;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonTutorial;
    private Button buttonPractice;
    private Button buttonGame;
    private Button buttonProgress;

    private Toolbar myToolbar;

    private NotificationManager notificationManager;
    private int notificationID = 1;

    private AlarmManager alarmManager;

    private static boolean isAlarmMangerActive = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate wird ausgeführt");
        setContentView(R.layout.start_activity);
        setUpUI();
        initDatabase();
        setupToolbar();
    }


    private void initDatabase(){
        NNCDatabase db = new NNCDatabase(this);
        db.open();
        db.close();

    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.start_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setLogo(R.drawable.ic_logo);
        //getSupportActionBar().setIcon(R.drawable.settings_actionbar_icon);

    }

    /*
     * This method shows the menu (only settings icon here) in the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_settings_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*
     * This method is the onClickListener and onClick-Method for the menu-item (settings icon)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(StartActivity.this, SettingsActivity.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    /*
    private void setupUI() {
        buttonTutorial = (Button) findViewById(R.id.start_tutorial_button);
        buttonTutorial.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, TutorialMainActivity.class);
                startActivity(i);
            }
        });
        buttonPractice = (Button) findViewById(R.id.start_practice_button);
        buttonGame = (Button) findViewById(R.id.start_game_button);
        buttonGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent (StartActivity.this, GameMainActivity.class);
                startActivity(i);
            }
        });

        buttonProgress = (Button) findViewById(R.id.start_progress_button);
        buttonProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, ProgressActivity.class);
                startActivity(i);
            }
        });

    }
*/


    // Meiner Meinung nach elegantere Lösung für das Verbinden der Buttons mit Click Listener, man
    // müsste nur oben noch implements OnClickListener schreiben


    private void setUpUI() {
        buttonTutorial = (Button) findViewById(R.id.start_tutorial_button);
        buttonTutorial.setOnClickListener(this);
        buttonPractice = (Button) findViewById(R.id.start_practice_button);
        buttonPractice.setOnClickListener(this);
        buttonGame = (Button) findViewById(R.id.start_game_button);
        buttonGame.setOnClickListener(this);
        buttonProgress = (Button) findViewById(R.id.start_progress_button);
        buttonProgress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.start_tutorial_button:
                i = new Intent(StartActivity.this, TutorialMainActivity.class);
                break;
            case R.id.start_practice_button:
                i = new Intent(StartActivity.this, PracticeMainActivity.class);
                break;
            case R.id.start_game_button:
                i = new Intent(StartActivity.this, GameMainActivity.class);
                break;
            case R.id.start_progress_button:
                i = new Intent(StartActivity.this, ProgressActivity.class);
                break;
            default:
                break;
        }

        if (i != null) {
            startActivity(i);
        }
    }

   /* private void showNotification (){
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("Message")
                .setContentText("MessageTex")
                .setSmallIcon(R.drawable.icon);
        PendingIntent resultIntent = new PendingIntent(StartActivity.this, StartActivity.class);
        notificationBuilder.setContentIntent(resultIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID,notificationBuilder.build());
    }*/

    /*
     * This method calls the normal onStop() method as well as the setAlarm() method, so that the user gets a push notification after
     * two weeks of not using the app. If the StartActivity is started again during the two weeks, the notification will not be shown
     * as you can see in onStart().
     */
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop wird ausgeführt");
        setAlarm();
    }

    /*
     * This method calls the normal onStart() method and sets the boolean isAlarmManagerActive to false, so that the push notification is not shown even
     * if the alarmManager is running
     */
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart wird ausgeführt");
        isAlarmMangerActive = false;
    }

    //this Method starts the alarmManager. The alarmManager starts the alertIntent after the alertTime (in this case after 5 Seconds
    public void setAlarm(){
        Long alertTime = new GregorianCalendar().getTimeInMillis()+1000*60*60*24*14;
        Intent alertIntent = new Intent(StartActivity.this, AlertReceiver.class);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(StartActivity.this,1,alertIntent,PendingIntent.FLAG_UPDATE_CURRENT));
        isAlarmMangerActive = true;
        System.out.println("setAlarm wurde ausgeführt");
    }

    /*@Override
    protected void onDestroy() {
        System.out.println("onDestroy1");
        setAlarm();
        System.out.println("onDestroy2");
        super.onDestroy();
        System.out.println("onDestroy");
    }*/

    // This method returns the boolean isAlarmManagerActive that the AlertReceiver has access
    public static boolean getAlarmManagerActive(){
        return isAlarmMangerActive;
    }
}
