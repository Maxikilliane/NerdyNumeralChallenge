package de.mi.ur.LevelLogic;

import de.mi.ur.Constants;

/**
 * Created by Anna-Marie on 07.09.2016.
 */
public class DifficultyCalculator {

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

    private static int getMaxNumeralBase(int numeral1Base, int numeral2Base){
        if(numeral1Base > numeral2Base){
            return numeral1Base;
        }else{
            return numeral2Base;
        }
    }

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
