package de.mi.ur.QuestionLogic;

/**
 * Created by Anna-Marie on 02.08.2016.
 */
public class TrueFalseQuestion extends Question {
    private String question;
    private String[] comparators = {"=", "≠","<", ">" };
    private boolean rightAnswer;


    public TrueFalseQuestion(int numeral1Base, int numeral2Base, int maxDigits) {
        super(numeral1Base, numeral2Base, maxDigits);
        this.question = generateQuestion(numeral1Base, numeral2Base, maxDigits);
    }

    private String generateQuestion(int numeral1Base, int numeral2Base, int maxDigits){
        String num1 = generateNumWithMaxDigits(numeral1Base, maxDigits);
        int numInt1 = convertFromNumeral(num1, numeral1Base);
        String comparator = comparators[getRandomGenerator().nextInt(4)];
        this.rightAnswer = getRandomGenerator().nextBoolean();
        String num2;
        int numInt2 ;
        if(rightAnswer){
            switch(comparator){
                case"=":
                    num2 = convertFromNumeralToNumeral(num1, numeral1Base, numeral2Base);
                    break;
                case"≠":
                    num2 = generateNumWithMaxDigits(numeral2Base, maxDigits);
                    if(convertFromNumeral(num2, numeral2Base) == convertFromNumeral(num1, numeral1Base)){
                        num2 = num2 +"1"; // nicht so ganz ideale Lösung, da dann längere Zahl
                    }
                    break;
                case"<":
                    numInt2 = numInt1 + getRandomGenerator().nextInt(20);
                    num2 = convertToNumeral(numInt2, numeral2Base);
                    break;
                case ">":
                    numInt2 = numInt1 - getRandomGenerator().nextInt(20);
                    num2 = convertToNumeral(numInt2, numeral2Base);
                    break;
                default:
                    num2 = "-1";
            }
        }else{
            switch(comparator){
                case"=":
                    num2 = generateNumWithMaxDigits(numeral2Base, maxDigits);
                    if(convertFromNumeral(num2, numeral2Base) == convertFromNumeral(num1, numeral1Base)){
                        num2 = num2 +"1"; // nicht so ganz ideale Lösung, da dann längere Zahl
                    }
                    break;
                case"≠":
                    num2 = convertFromNumeralToNumeral(num1, numeral1Base, numeral2Base);
                    break;
                case"<":
                    numInt2 = numInt1 - getRandomGenerator().nextInt(20);
                    num2 = convertToNumeral(numInt2, numeral2Base);
                    break;
                case ">":
                    numInt2 = numInt1 + getRandomGenerator().nextInt(20);
                    num2 = convertToNumeral(numInt2, numeral2Base);
                    break;
                default:
                    num2 = "-1";
            }


        }

        return num1+" "+comparator+" "+num2;
    }

    public String getQuestion(){
        return question;
    }

    public String getRightAnswer(){
        if(rightAnswer){
            return "true";
        }else{
            return "false";
        }
    }

}
