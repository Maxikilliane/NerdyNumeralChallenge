package de.mi.ur.LevelLogic;

import de.mi.ur.Constants;

/**
 * Created by Anna-Marie on 07.09.2016.
 *
 * This class is responsible for calculating how many points the user gets per correct answer in practice mode
 * and how long the question-numbers are
 */
public class DifficultyCalculator {

    /*
     * The larger of the two chosen numeral bases and the question type are responsible for the number of points the
     * user receives per correctly answered question
     *
     * If the question and answer system are the system, the user does not get points.
     *
     * The well-known systems from which you are able to transform by simple alignment (e.g. 2 -> 10 etc.)
     * give the least points (2)
     * 9<->3 is less known and 4 <-> 16 is a bit harder, you need two alignments in your head, so you get three points
     * transformations to and from 10 are easier, because you know the system well -> 4 points
     * If the bigger numeral system base is lower than 10 -> 5 points
     * If it is higher than 10 -> 6 points
     *
     * FreeTextQuestions give 2 points more than the other two questiontypes
     */
public static int getPointsPerQuestion(int questionType, int numeral1Base, int numeral2Base){
    int pointsPerQuestion = 0;
    int biggerNumeralBase = getMaxNumeralBase(numeral1Base, numeral2Base);
    if(numeral1Base == numeral2Base){
        return pointsPerQuestion;
    }else{
        if(
                (numeral1Base == 2 &&( numeral2Base == 4 || numeral2Base ==8 || numeral2Base == 16 )) ||
                (numeral2Base == 2 &&( numeral1Base == 4 || numeral1Base ==8 || numeral1Base == 16 )) ||
                (numeral1Base == 4 && numeral2Base ==16) || (numeral1Base == 16 && numeral2Base == 4)

                ){
            pointsPerQuestion = 2;
        }else if ((numeral1Base == 8 && (numeral2Base == 4 || numeral2Base == 16)) ||
                (numeral2Base == 8 && (numeral1Base == 4 || numeral1Base == 16)) ||
                (numeral1Base == 3 && numeral2Base ==9) || (numeral1Base ==9 && numeral2Base == 3 )
                ){
            pointsPerQuestion = 3;
        }else if(numeral1Base == 10 || numeral2Base == 10){
            pointsPerQuestion = 4;
        }else if (biggerNumeralBase <= 9){
            pointsPerQuestion = 5;
        }else if (biggerNumeralBase > 10){
            pointsPerQuestion = 6;
        }
        if(questionType == Constants.FREETEXT){
            pointsPerQuestion += 2;
        }
    }
    return pointsPerQuestion;
}

    /*
     * returns the maximum of two given numeralBases
     */
    private static int getMaxNumeralBase(int numeral1Base, int numeral2Base){
        if(numeral1Base > numeral2Base){
            return numeral1Base;
        }else{
            return numeral2Base;
        }
    }


    /*
     * Determs the basic question length in practicemode (depending on the concerned numeralsystems)
     * Later, length is added depending on the current level of the user
     */
    public static int getBaseQuestionLength(int numeral1Base, int numeral2Base){
        if(numeral1Base > numeral2Base){
            if(numeral1Base <= 4){
                return 4;
            }else if (numeral2Base <= 8) {
                return 3;
            } else if (numeral2Base <= 10){
                return 2;
            } else if (numeral1Base > 10){
                return 1;
            } else{
                return 1;
            }

        }else{
            if(numeral1Base ==2 ){
                return 4;
            }else if (numeral1Base <= 4){
                return 3;
            }else if (numeral1Base <= 8){
                return 2;
            }else if (numeral1Base >8){
                return 1;
            }else{
                return 1;
            }
        }
    }

}
