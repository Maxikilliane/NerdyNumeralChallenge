package de.mi.ur;

/**
 * Created by Anna-Marie on 01.08.2016.
 */
public class Constants {
    // Abfrage in ExplanationActivity, welches Tutorial angezeigt werden soll
    public static final String KEY_TYPE_TUTORIAL = "type of tutorial";

    public static final int INTRO_TUTORIAL = 0;
    public static final int DECIMAL_TUTORIAL = 1;
    public static final int OTHER_TUTORIAL = 2;
    public static final int TRICKS_TUTORIAL = 3;

    public static final String KEY_NUMBER_TUTORIAL = "number of explanation";
    //evtl brauche ich die ganzen Number-Konstanten nicht (außer die erste), weil ich das jetzt ohne Intents mache (also nicht die Activity neu aufrufe...)

    public static final int NUMBER_1 = 0;
    public static final int NUMBER_2 = 1;
    public static final int NUMBER_3 = 2;
    public static final int NUMBER_4 = 3;
    public static final int NUMBER_5 = 4;
    public static final int NUMBER_6 = 5;

    //Anzahl der Screens pro Tutorial -> Zählanfang im Array bei 0, d.h. das ist der letzte zugreifbare Punkt im jeweiligen String-Array
    public static final int MAX_EXPLANATION_NUM_INTRO = 5;
    public static final int MAX_EXPLANATION_NUM_DECIMAL = 3;
    public static final int MAX_EXPLANATION_NUM_OTHER = 5;
    public static final int MAX_EXPLANATION_NUM_TRICKS = 5;





}