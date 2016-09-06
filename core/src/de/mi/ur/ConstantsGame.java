package de.mi.ur;

import de.mi.ur.sprites.Nerd;
import de.mi.ur.states.PlayState;

/**
 * Created by Anna-Marie on 05.08.2016.
 */
public class ConstantsGame {

        private static Nerd nerd = new Nerd(0,0);

        public static final int NERD_GRAVITY_DEFAULT = -30;
        public static final int NERD_MOVEMENT_DEFAULT = 100;
        public static final int NERD_POSITION_OFFSET = 80;
        public static final int NERD_X = 40;
        public static final int NERD_Y = 200;


        public static final int BOUNDS_OFFSET = -50;

        public static final int PIT_WIDTH = 52;
        public static final int PIT_BOUNDS_OFFSET = 0;
        public static final int PIT_OFFSET = 180;
        public static final int PIT_NUM = 4;

        public static final int NERD_BOUNDS_OFFSET = nerd.getWidth()/2;

        public static final int GROUND_Y_OFFSET = -30;

        public static final int SCORE_OFFSET_Y =160;
        public static final int SCORE_OFFSET_X = -110;
        public static final int SCORE_HEARTS_OFFSET_X = 20;
        public static final int SCORE_HEARTS_OFFSET_Y = 140;

        public static final int QUESTION_OFFSET_X = -55;
                public static final int QUESTION_OFFSET_Y = 120;

        public static final int QUESTION_ANSWER_OFFSET_X = -5;
        public static final int QUESTION_ANSWER_OFFSET_Y = -10;

        public static final int WOMAN_Y = PlayState.ground.getHeight() + GROUND_Y_OFFSET;



        // Damit kann man später erfahren, welcher Hintergrund verwendet werden soll. Zugriff in der NerdyNumeralChallenge-Klasse
        //möglich
        public static final int WEATHER_SUNNY = 0;
        public static final int WEATHER_CLOUDY = 1;
        public static final int WEATHER_RAINY = 2;
        public static final int WEATHER_SNOWY = 3;

        //Array-Positionen für Frage und Antwort
        public static final int QUESTION_POS = 0;
        public static final int RIGHT_ANSWER_POS = 1;
        public static final int POSS_ANSWER1_POS = 2;
        public static final int POSS_ANSWER2_POS = 3;
        public static final int POSS_ANSWER3_POS = 4;
        public static final int POSS_ANSWER4_POS = 5;


        // für Random-Generator
        public static final int PIT_TYPE = 0;
        public static final int WOMAN_TYPE = 1;



}
