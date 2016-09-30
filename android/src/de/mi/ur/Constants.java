package de.mi.ur;

/**
 * Created by Anna-Marie on 01.08.2016.
 */
public class Constants {
    //communication between PracticeMain and PractiseActivities
    public static final String KEY_TYPE_QUESTION = "type of practise question";
    public static final int MULTIPLE_CHOICE = 0;
    public static final int TRUE_FALSE = 1;
    public static final int FREETEXT = 2;
    public static final String KEY_NUMERAL_1_BASE = "numeral1 base";
    public static final String KEY_NUMERAL_2_BASE = "numeral2 base";

    public static final String KEY_QUESTION_LENGTH = "question length";

    // Abfrage in ExplanationActivity, welches Tutorial angezeigt werden soll
    public static final String KEY_TYPE_TUTORIAL = "type of tutorial";

    public static final int INTRO_TUTORIAL = 0;
    public static final int DECIMAL_TUTORIAL = 1;
    public static final int OTHER_TUTORIAL = 2;
    public static final int TRICKS_TUTORIAL = 3;

    public static final String KEY_NUMBER_TUTORIAL = "number of explanation";

    public static final int NUMBER_1 = 0;

    //Anzahl der Screens pro Tutorial -> Zählanfang im Array bei 0, d.h. das ist der letzte zugreifbare Punkt im jeweiligen String-Array
    public static final int MAX_EXPLANATION_NUM_INTRO = 5;
    public static final int MAX_EXPLANATION_NUM_DECIMAL = 3;
    public static final int MAX_EXPLANATION_NUM_OTHER = 5;
    public static final int MAX_EXPLANATION_NUM_TRICKS = 5;


    public static final int WEATHER_SUNNY = 0;
    public static final int WEATHER_CLOUDY = 1;
    public static final int WEATHER_RAINY = 2;
    public static final int WEATHER_SNOWY = 3;
    public static final int WEATHER_DEFAULT = 0;

    //Extra für AndroidLauncher
    public static final String CURRENT_WEATHER = "current weather";
    public static final String BACKGROUND_MUSIC = "background music";
    public static final String SOUND_EFFECTS = "sound effects";

    //Permission
    public static final int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 111;

    //Practice
    public static final int NUM_QUESTIONS_PER_PRACTICE = 10;
    public static final int PROGRESS_FULL = 100;
    public static final int DELAY_HALF_SECOND = 500;
    public static final int BACK_KEY_PRESSED = -1;

    //Questions
    public static final int NUM_WRONG_ANSWERS_MULTIPLE_CHOICE = 3;
    public static final int MAX_NUMERAL_BASE = 16;
    public static final int MIN_NUMERAL_BASE = 2;
    public static final int DEFAULT_NUMERAL_BASE = 10;


    //MultipleChoiceDialog
    public static final int MULTIPLE_CHOICE_DIALOG_FIRST_NUMERAL_BASE = 2;
    public static final int MULTIPLE_CHOICE_DIALOG_SECOND_NUMERAL_BASE = 10;
    public static final int MULTIPLE_CHOICE_DIALOG_QUESTION_LENGTH = 6;
    public static final int DIALOG_SHOW_TIME_IN_SECONDS = 5;

    //WeatherExtras

    public static final String API_ID = "50ddb65d4b6d51050e5844a4284d6d46";
    public static final String DEFAULT_LATITUDE = "49";
    public static final String DEFAULT_LONGITUDE = "12";
    public static final String WEATHER_API_URL_1_LAT = "http://api.openweathermap.org/data/2.5/weather?lat=";
    public static final String WEATHER_API_URL_2_LON = "&lon=";
    public static final String WEATHER_API_URL_3 = "&appid=" + API_ID;

    //Database
    public static final int HIHGEST_LEVEL = 9;

    public static final int CURRENT_LEVEL_ID = 20;
    public static final String[] levelNames = {"Unwissender",
            "Initiant",
            "Padawan",
            "Nullen-Nerd",
            "edler Einsen-Verehrer",
            "Quaternal-Kenner",
            "Oktal-Jongleur",
            "Hex-Beherrscher",
            "Meister der Systeme",
            "5up3r N3rd"
    };

    public static final int LEVEL_NUM_0 = 0;
    public static final int LEVEL_NUM_1 = 1;
    public static final int LEVEL_NUM_2 = 2;
    public static final int LEVEL_NUM_3 = 3;
    public static final int LEVEL_NUM_4 = 4;
    public static final int LEVEL_NUM_5 = 5;
    public static final int LEVEL_NUM_6 = 6;
    public static final int LEVEL_NUM_7 = 7;
    public static final int LEVEL_NUM_8 = 8;
    public static final int LEVEL_NUM_9 = 9;

    public static final int NUM_POINTS_LVL_0 = 0;
    public static final int NUM_POINTS_LVL_1 = 100;
    public static final int NUM_POINTS_LVL_2 = 300;
    public static final int NUM_POINTS_LVL_3 = 600;
    public static final int NUM_POINTS_LVL_4 = 1000;
    public static final int NUM_POINTS_LVL_5 = 1500;
    public static final int NUM_POINTS_LVL_6 = 2100;
    public static final int NUM_POINTS_LVL_7 = 2800;
    public static final int NUM_POINTS_LVL_8 = 3600;
    public static final int NUM_POINTS_LVL_9 = 4500;

    public static final int QUESTION_DIFFICULTY_EASY = 0;
    public static final int QUESTION_DIFFICULTY_MEDIUM = 1;
    public static final int QUESTION_DIFFICULTY_HARD = 2;
    public static final int QUESTION_DIFFICULTY_REALLY_HARD = 3;

    //Font
    public static final String FONT = "cantarell_font.ttf";



}
