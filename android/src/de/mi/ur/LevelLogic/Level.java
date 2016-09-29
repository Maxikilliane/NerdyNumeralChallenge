package de.mi.ur.LevelLogic;

/**
 * Created by Anna-Marie on 07.09.2016.
 *
 * This class represent a Level the User can reach in Practicemode
 *
 * remark concerning pointsNeededForThisLevel: This is the amount of points the user needs to have to be in this level;
 * in case of the currentLevel of the user, the points he/she currently has, are stored in this variable
 */
public class Level {
    private int id;
    private int levelNum;
    private String levelName;
    private int pointsNeededForThisLevel;
    private int questionLength;

    public Level(int id, int levelNum, String levelName, int pointsNeededForThisLevel, int questionLength){
        this.id = id;
        this.levelNum = levelNum;
        this.levelName = levelName;
        this.pointsNeededForThisLevel = pointsNeededForThisLevel;
        this.questionLength = questionLength;
    }

    public int getId(){
        return id;
    }


    public int getLevelNum() {
        return levelNum;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getPointsNeededForThisLevel() {
        return pointsNeededForThisLevel;
    }

    public int getQuestionLength() {
        return questionLength;
    }
}
