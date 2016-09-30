package de.mi.ur.QuestionLogic;

import java.util.Random;

/**
 * Created by Anna-Marie on 02.08.2016.
 *
 * This class is the superclass for the questions, and makes it possible to have an instance variable where
 * the practice-questions can be saved, regardless of which kind of question it is.
 * The methods do not have any functionality.
 */
public abstract class Question {
    private static Random randomGen;
    private int numeral1Base;
    private int numeral2Base;
    private int maxDigits;

    public boolean isCorrectAnswer(String answer) {
        return false;
    }

    public String getQuestionString() {
        return "";
    }

    public String getRightAnswerString() {
        return "";
    }

    //numeral1Base ist die Basis des Fragesystems, numeral2Base ist die Basis des Antwortsystems
    public Question (int numeral1Base, int numeral2Base, int maxDigits){
        randomGen = new Random();
        this.numeral1Base = numeral1Base;
        this.numeral2Base = numeral2Base;
        this.maxDigits = maxDigits;
    }



    /*
     * Eine int-Zahl in einen String als Darstellung eines anderen Zahlensystems umrechnen
     */

    /*
    public static String convertToNumeral(int number, int radix){
        if(radix <2 || radix>16){
            radix = 10;
        }
        String toReturn = Integer.toString(number, radix);
        return toReturn.toUpperCase();
    }

    /*
     * Eine String-Darstellung einer Zahl in einem Zahlensystem der Basis radix in eine int-Zahl (Dezimal) umrechnen
     */

    /*
    public static int convertFromNumeral(String number, int radix){
        return Integer.parseInt(number, radix);
    }

    public static String convertFromNumeralToNumeral(String number, int radixInput, int radixOutput){
        int num = convertFromNumeral(number, radixInput);
        return convertToNumeral(num, radixOutput);
    }

    /*
    //gibt random int-Werte zwischen minInclusive und maxInclusive zurück
    public static int generateRandomBetweenBounds(int minInclusive, int maxInclusive){
        int bounds = maxInclusive - minInclusive;
        int random = randomGen.nextInt(bounds+1);
        return random+minInclusive;
    }
    */

    /*
     * Generiert eine Zahl im Zahlensystem mit der Basis numeralBase und mit maxDigits Ziffern
     */

    /*
    public static String generateNumWithMaxDigits(int numeralBase, int maxDigits){
        String number = "";
        for(int i = 0; i<maxDigits; i++){
            int num = randomGen.nextInt(numeralBase);
            number += convertToNumeral(num, numeralBase);
        }
        return number;
    }

    public static String generateNumBelowMax(int destinationNumeralBase, int maxDecimal){
        int num = randomGen.nextInt(maxDecimal);
        return convertToNumeral(num, destinationNumeralBase);
    }

    */

    public String[] generatePossAnswers() {
        return null;
    }

    public String getQuestion(){
        return null;
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
