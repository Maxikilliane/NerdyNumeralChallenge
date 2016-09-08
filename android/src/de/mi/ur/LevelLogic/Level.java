package de.mi.ur.LevelLogic;

/**
 * Created by Anna-Marie on 07.09.2016.
 */
public class Level {
    private int id;
    private int levelNum;
    private String levelName;
    private int pointsNeededForThisLevel;
    private int questionLength;

    public Level(int id, int levelNum, String levelName, int pointsNeededForThisLevel, int questionLength) {
        this.id = id;
        this.levelNum = levelNum;
        this.levelName = levelName;
        this.pointsNeededForThisLevel = pointsNeededForThisLevel;
        this.questionLength = questionLength;
    }

    public int getId() {
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
