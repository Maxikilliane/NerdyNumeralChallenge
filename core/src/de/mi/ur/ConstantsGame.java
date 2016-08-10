package de.mi.ur;

import de.mi.ur.sprites.Nerd;

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


        public static final int PIT_WIDTH = 52;
        public static final int PIT_BOUNDS_OFFSET = nerd.getWidth()/2;
        public static final int PIT_OFFSET = 180;
        public static final int PIT_NUM = 4;

        public static final int NERD_BOUNDS_OFFSET = nerd.getWidth()/2;

        public static final int GROUND_Y_OFFSET = -30;

        public static final int SCORE_OFFSET_Y =160;
        public static final int SCORE_OFFSET_X = -110;
        public static final int SCORE_HEARTS_OFFSET_X = 20;
        public static final int SCORE_HEARTS_OFFSET_Y = 140;




}
