package de.mi.ur.QuestionLogic;

import java.util.Random;

/**
 * Created by Anna-Marie on 02.08.2016.
 */
public abstract class Question {
    private static Random randomGen;
    private int numeral1Base;
    private int numeral2Base;
    private int maxDigits;

    public Question (int numeral1Base, int numeral2Base, int maxDigits){
        randomGen = new Random();
    }

    public static String convertToNumeral(int number, int radix){
        if(radix <2 || radix>16){
            radix = 10;
        }
        String toReturn = Integer.toString(number, radix);
        return toReturn.toUpperCase();
    }

    public static int convertFromNumeral(String number, int radix){
        return Integer.parseInt(number, radix);
    }


    //nochmal durchrechnen
    public static int generateRandomBetweenBounds(int minInclusive, int maxInclusive){
        int bounds = maxInclusive - minInclusive;
        int random = randomGen.nextInt(bounds+1);
        return random+minInclusive;
    }

    public int getNumeral1Base(){
        return this.numeral1Base;
    }

    public int getNumeral2Base(){
        return this.numeral2Base;
    }

    public int getMaxDigits(){
        return this.maxDigits;
    }

    public Random getRandomGenerator(){
        return randomGen;
    }
}
