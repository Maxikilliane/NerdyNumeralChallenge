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
        String num1 = NumeralConverter.generateNumWithMaxDigits(numeral1Base, maxDigits);
        int numInt1 = NumeralConverter.convertFromNumeral(num1, numeral1Base);
        String comparator = comparators[getRandomGenerator().nextInt(4)];
        this.rightAnswer = getRandomGenerator().nextBoolean();
        String num2;
        int numInt2 ;
        int limit = (int) Math.pow(numeral1Base, maxDigits);
        if(this.rightAnswer){
            switch(comparator){
                case"=":
                    num2 = NumeralConverter.convertFromNumeralToNumeral(num1, numeral1Base, numeral2Base);
                    break;
                case"≠":
                    num2 = NumeralConverter.generateNumBelowMax(numeral2Base, limit);
                    if(NumeralConverter.convertFromNumeral(num2, numeral2Base) == NumeralConverter.convertFromNumeral(num1, numeral1Base)){
                        num2 = num2 +"1";
                    }
                    break;
                case"<":
                    numInt2 = numInt1 + getRandomGenerator().nextInt(15);
                    num2 = NumeralConverter.convertToNumeral(numInt2, numeral2Base);
                    break;
                case ">":
                    numInt2 = numInt1 - getRandomGenerator().nextInt(15);
                    if(numInt2 < 0 && numInt1 != 0){
                        numInt2 = numInt1 - 1;
                    }
                    num2 = NumeralConverter.convertToNumeral(numInt2, numeral2Base);
                    break;
                default:
                    num2 = "-1";
            }
        }else{
            switch(comparator){
                case"=":
                    num2 = NumeralConverter.generateNumBelowMax(numeral2Base, limit);
                    if(NumeralConverter.convertFromNumeral(num2, numeral2Base) == NumeralConverter.convertFromNumeral(num1, numeral1Base)){
                        num2 = num2 +"1";
                    }
                    break;
                case"≠":
                    num2 = NumeralConverter.convertFromNumeralToNumeral(num1, numeral1Base, numeral2Base);
                    break;
                case"<":
                    numInt2 = numInt1 - getRandomGenerator().nextInt(10);
                    num2 = NumeralConverter.convertToNumeral(numInt2, numeral2Base);
                    break;
                case ">":
                    numInt2 = numInt1 + getRandomGenerator().nextInt(10);
                    num2 = NumeralConverter.convertToNumeral(numInt2, numeral2Base);
                    break;
                default:
                    num2 = "-1";
            }


        }

        return num1+" (Basis "+numeral1Base+") "+comparator+" "+num2+" (Basis "+numeral2Base+")";
    }

    public String getQuestion(){
        return question;
    }

    public String getRightAnswerString(){
        if(rightAnswer){
            return "wahr";
        }else{
            return "falsch";
        }
    }


}
