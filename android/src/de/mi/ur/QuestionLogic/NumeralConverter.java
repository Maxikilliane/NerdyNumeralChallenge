package de.mi.ur.QuestionLogic;

import java.util.Random;

/**
 * Created by Anna-Marie on 06.09.2016.
 */
public class NumeralConverter {
    private static Random randomGen = new Random();

    public NumeralConverter(){}

    /*
     * Eine int-Zahl in einen String als Darstellung eines anderen Zahlensystems umrechnen
     */
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
    public static int convertFromNumeral(String number, int radix){
        return Integer.parseInt(number, radix);
    }

    /*
    * String-Darstellung einer Zahl wird von einem Zahlensystem der Basis radixInput in ein anderes der Basis radixOutput umgerechnet
     */
    public static String convertFromNumeralToNumeral(String number, int radixInput, int radixOutput){
        int num = convertFromNumeral(number, radixInput);
        return convertToNumeral(num, radixOutput);
    }

    /*
     * Generiert eine Zahl im Zahlensystem mit der Basis numeralBase und mit maxDigits Ziffern
     */
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
}
