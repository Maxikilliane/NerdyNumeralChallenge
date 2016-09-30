package de.mi.ur.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.Constants;
import de.mi.ur.LevelLogic.Level;

/**
 * Created by Anna-Marie on 09.08.2016.
 *
 * This class is responsible for storing the things needed after the shutdown of the application
 */
public class NNCDatabase implements HighscoreListener {
    // generall database
    private static final String DATABASE_NAME = "NNC Database";
    private static final int DATABASE_VERSION = 1;

    /*
     * highscore table constants
     */
    private static final String TABLE_HIGHSCORE = "nncGameHighscores";
    private static final String KEY_ID = "_id";
    private static final String KEY_RANK = "rank";
    private static final String KEY_POINTS = "points";
    private static final String KEY_NAME = "name";

    private static final int COLUMN_RANK_INDEX = 1;
    private static final int COLUMN_POINTS_INDEX = 2;
    private static final int COLUMN_NAME_INDEX = 3;

    /*
     * level table constants
     */
    private static final String TABLE_LEVEL = "nncLevel";
    private static final String KEY_LEVEL_ID = "_id";
    private static final String KEY_LEVEL_NUM = "level_number";
    private static final String KEY_LEVEL_NAME = "level_name";
    private static final String KEY_POINTS_FOR_NEXT_LEVEL = "points_for_next_level";
    private static final String KEY_QUESTION_LENGTH = "question_length";

    private static final int COLUMN_LEVEL_ID_INDEX = 0;
    private static final int COLUMN_LEVEL_NUM_INDEX = 1;
    private static final int COLUMN_LEVEL_NAME_INDEX = 2;
    private static final int COLUMN_POINTS_NEXT_LEVEL_INDEX = 3;
    private static final int COLUMN_QUESTION_LENGTH_INDEX = 4;


    /*
     * access to all columns of the database with this as a where-clause
     */
    private static final String[] ALL_COLUMNS_HIGHSCORE = {KEY_ID, KEY_RANK, KEY_POINTS, KEY_NAME};
    private static final String[] ALL_COLUMNS_LEVEL = {KEY_ID, KEY_LEVEL_NUM, KEY_LEVEL_NAME, KEY_POINTS_FOR_NEXT_LEVEL, KEY_QUESTION_LENGTH};

    // instance variables
    private NncDBOpenHelper dbHelper;
    private SQLiteDatabase database;

    public NNCDatabase(Context context) {
        dbHelper = new NncDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /*
     * gets a usable database
     */
    public void open() throws SQLException {
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            database = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        database.close();
    }


    /*
     * Saves a highscore in the database
     */
    public long insertHighscoreData(Highscore highscore) {
        ContentValues highscoreValues = new ContentValues();

        highscoreValues.put(KEY_RANK, highscore.getRank());
        highscoreValues.put(KEY_POINTS, highscore.getPoints());
        highscoreValues.put(KEY_NAME, highscore.getName());

        return database.insert(TABLE_HIGHSCORE, null, highscoreValues);
    }

    /*
     * removes a highscore with a certain rank from the database
     */
    public void removeHighscoreData (int rank){
        String whereClause = KEY_RANK + " = "+ rank;
        database.delete(TABLE_HIGHSCORE, whereClause, null);
    }


    /*
     * Gets a highscore with a certain rank from the database
     */
    public Highscore getHighscoreWithCertainRank(int rank) {
        String whereClause = KEY_RANK + " = " + rank;
        Cursor cursor = database.query(TABLE_HIGHSCORE, ALL_COLUMNS_HIGHSCORE, whereClause, null, null, null, null);
        ArrayList<Highscore> highscore = buildHighscoresFromCursor(cursor);

        return highscore.get(0);
    }

    /*
     * Gets an ArrayList of all Highscores saved in the database
     */
    public ArrayList<Highscore> getAllHighscores() {
        Cursor cursor = getAllHighscoresCursor();
        return buildHighscoresFromCursor(cursor);
    }

    /*
     * gets a Cursor pointing to all Highscores
     */
    public Cursor getAllHighscoresCursor() {
        return database.query(TABLE_HIGHSCORE, ALL_COLUMNS_HIGHSCORE, null, null, null, null, KEY_RANK + " ASC");
    }

    /*
     * "building" highscores is done in this method to avoid duplicate code
     */
    private ArrayList<Highscore> buildHighscoresFromCursor(Cursor cursor) {
        ArrayList<Highscore> highscores = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int _rank = cursor.getInt(COLUMN_RANK_INDEX);
                int points = cursor.getInt(COLUMN_POINTS_INDEX);
                String name = cursor.getString(COLUMN_NAME_INDEX);

                highscores.add(new Highscore(_rank, points, name));

            } while (cursor.moveToNext());
        }
        return highscores;
    }

    /*
     * "building" levels is done in this method, to avoid duplicate code
     */
    private ArrayList<Level> buildLevelFromCursor(Cursor cursor){
        ArrayList<Level> levels = new ArrayList<Level>();
        if(cursor.moveToFirst()){
            do{
                int levelId = cursor.getInt(COLUMN_LEVEL_ID_INDEX);
                int levelNum = cursor.getInt(COLUMN_LEVEL_NUM_INDEX);
                String levelName = cursor.getString(COLUMN_LEVEL_NAME_INDEX);
                int pointsNeeded = cursor.getInt(COLUMN_POINTS_NEXT_LEVEL_INDEX);
                int questionLength = cursor.getInt(COLUMN_QUESTION_LENGTH_INDEX);
                levels.add(new Level(levelId, levelNum, levelName, pointsNeeded, questionLength));

            }while(cursor.moveToNext());
        }
        return levels;
    }

    /*
     * gets the current Level of the user from the database
     */
    public Level getCurrentLevel(){
        String whereClause = KEY_LEVEL_ID + " = " + Constants.CURRENT_LEVEL_ID;
        Cursor cursor = database.query(TABLE_LEVEL, ALL_COLUMNS_LEVEL, whereClause, null, null, null, null);
        return buildLevelFromCursor(cursor).get(0);
    }

    /*
     * removes the current Level of the user from the database
     */
    private void removeCurrentLevel(){
        String whereClause = KEY_LEVEL_ID + " = " + Constants.CURRENT_LEVEL_ID;
        database.delete(TABLE_LEVEL, whereClause, null);
    }

    /*
     * updates the points in the current level of the user
     */
    public void insertCurrentLevelPoints(int points){
        Level currentLevel = getCurrentLevel();

        ContentValues levelValues = new ContentValues();
        levelValues.put(KEY_LEVEL_ID, currentLevel.getId());
        levelValues.put(KEY_LEVEL_NUM, currentLevel.getLevelNum());
        levelValues.put(KEY_LEVEL_NAME, currentLevel.getLevelName());
        levelValues.put(KEY_POINTS_FOR_NEXT_LEVEL, points);
        levelValues.put(KEY_QUESTION_LENGTH, currentLevel.getQuestionLength());
        removeCurrentLevel();
        database.insert(TABLE_LEVEL, null, levelValues);
    }

    /*
     * gets the level with the given ID from the database
     * levelId should be between 0 and 0
     * to get the currentLevel, please use the appropriate method
     */
    public Level getLevel(int levelId) {
        if (levelId < 0 || levelId > 9) {
            levelId = 0;
        }
        String whereClause = KEY_LEVEL_ID + " = " + levelId;
        Cursor cursor = database.query(TABLE_LEVEL, ALL_COLUMNS_LEVEL, whereClause, null, null, null, null);
        return buildLevelFromCursor(cursor).get(0);
    }

    /*
     * Checks whether the user advances to the next level,
     * and if so, changes the information in the currentLevel accordingly
     */
    public boolean checkIfNextLevel(){
        Level currentLevel = getCurrentLevel();
        int currentLevelNum = currentLevel.getLevelNum();
        if (currentLevelNum < Constants.HIHGEST_LEVEL) {
            Level nextLevel = getLevel(currentLevelNum + 1);

            if (currentLevel.getPointsNeededForThisLevel() >= nextLevel.getPointsNeededForThisLevel()) {
                removeCurrentLevel();
                ContentValues levelValues = new ContentValues();
                levelValues.put(KEY_LEVEL_ID, currentLevel.getId());
                levelValues.put(KEY_LEVEL_NUM, nextLevel.getLevelNum());
                levelValues.put(KEY_LEVEL_NAME, nextLevel.getLevelName());
                levelValues.put(KEY_POINTS_FOR_NEXT_LEVEL, currentLevel.getPointsNeededForThisLevel());
                levelValues.put(KEY_QUESTION_LENGTH, nextLevel.getQuestionLength());

                database.insert(TABLE_LEVEL, null, levelValues);

                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /*
     * checks if points is large enough to be a new highscore.
     * returns the rank in the highscore, or -1 if the points are not good enough for a highscore
     */
    @Override
    public int checkIfNewHighscore(int points) {
        if (getAllHighscores().size() == 0 || getHighscoreWithCertainRank(1).getPoints() < points) {
            return 1;
        } else if (getAllHighscores().size() == 1 || getHighscoreWithCertainRank(2).getPoints() < points) {
            return 2;
        } else if (getAllHighscores().size() == 2 || getHighscoreWithCertainRank(3).getPoints() < points) {
            return 3;
        } else {
            return -1;
        }
    }

    /*
     * This method saves the new highscore to the database
     * all the old highscores are adapted to the new one
     */
    @Override
    public void saveHighscoreToDatabase(int rank, int points, String newUserName) {
        ArrayList<Highscore> allHighscores = getAllHighscores();
        int size = allHighscores.size();
        for(int i = size; i > rank; i--){
            removeHighscoreData(i);
            Highscore newHighscore = allHighscores.get(i-2);
            newHighscore.lowerRankByOne();
            insertHighscoreData(newHighscore);
        }
        if((size==rank) && (size != 3)){
            removeHighscoreData(rank);
            Highscore newHighscore = allHighscores.get(size-1);
            newHighscore.lowerRankByOne();
            insertHighscoreData(newHighscore);
        }
        removeHighscoreData(rank);
        insertHighscoreData(new Highscore(rank, points, newUserName));

    }


    private class NncDBOpenHelper extends SQLiteOpenHelper {
        private static final String CREATE_TABLE = "create table ";
        private static final String INTEGER_NOT_NULL = " integer not null, ";
        private static final String INTEGER_1_KEY_AUTOINCREMENT = " integer primary key autoincrement, ";

        public static final String CREATE_HIGHSCORE_TABLE = CREATE_TABLE + TABLE_HIGHSCORE
                + " (" + KEY_ID + INTEGER_1_KEY_AUTOINCREMENT + KEY_RANK + INTEGER_NOT_NULL
                + KEY_POINTS + INTEGER_NOT_NULL + KEY_NAME + " text);";

        public static final String CREATE_LEVEL_TABLE = CREATE_TABLE + TABLE_LEVEL
                + " ("+ KEY_ID + INTEGER_NOT_NULL + KEY_LEVEL_NUM + INTEGER_NOT_NULL
                + KEY_LEVEL_NAME + " text, " + KEY_POINTS_FOR_NEXT_LEVEL + INTEGER_NOT_NULL
                + KEY_QUESTION_LENGTH + " integer not null);";

            public NncDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

        /*
         * The two tables are initiated
         * and the level-table is filled with the information on levels.
         * The current level is the same as level 0 (except for the id) at the beginning
         */
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_HIGHSCORE_TABLE);
                db.execSQL(CREATE_LEVEL_TABLE);

                String[] levelNames = Constants.levelNames;

                Level[] levels = {
                        new Level(Constants.LEVEL_NUM_0, Constants.LEVEL_NUM_0, levelNames[0], Constants.NUM_POINTS_LVL_0, Constants.QUESTION_DIFFICULTY_EASY),
                        new Level(Constants.LEVEL_NUM_1, Constants.LEVEL_NUM_1, levelNames[1], Constants.NUM_POINTS_LVL_1, Constants.QUESTION_DIFFICULTY_EASY),
                        new Level(Constants.LEVEL_NUM_2, Constants.LEVEL_NUM_2, levelNames[2], Constants.NUM_POINTS_LVL_2, Constants.QUESTION_DIFFICULTY_EASY),
                        new Level(Constants.LEVEL_NUM_3, Constants.LEVEL_NUM_3, levelNames[3], Constants.NUM_POINTS_LVL_3, Constants.QUESTION_DIFFICULTY_EASY),
                        new Level(Constants.LEVEL_NUM_4, Constants.LEVEL_NUM_4, levelNames[4], Constants.NUM_POINTS_LVL_4, Constants.QUESTION_DIFFICULTY_MEDIUM),
                        new Level(Constants.LEVEL_NUM_5, Constants.LEVEL_NUM_5, levelNames[5], Constants.NUM_POINTS_LVL_5, Constants.QUESTION_DIFFICULTY_MEDIUM),
                        new Level(Constants.LEVEL_NUM_6, Constants.LEVEL_NUM_6, levelNames[6], Constants.NUM_POINTS_LVL_6, Constants.QUESTION_DIFFICULTY_MEDIUM),
                        new Level(Constants.LEVEL_NUM_7, Constants.LEVEL_NUM_7, levelNames[7], Constants.NUM_POINTS_LVL_7, Constants.QUESTION_DIFFICULTY_HARD),
                        new Level(Constants.LEVEL_NUM_8, Constants.LEVEL_NUM_8, levelNames[8], Constants.NUM_POINTS_LVL_8, Constants.QUESTION_DIFFICULTY_HARD),
                        new Level(Constants.LEVEL_NUM_9, Constants.LEVEL_NUM_9, levelNames[9], Constants.NUM_POINTS_LVL_9, Constants.QUESTION_DIFFICULTY_REALLY_HARD),
                        new Level(Constants.CURRENT_LEVEL_ID, Constants.LEVEL_NUM_0, levelNames[0], Constants.NUM_POINTS_LVL_0, Constants.QUESTION_DIFFICULTY_EASY)
                };

                for (Level level : levels) {
                    ContentValues levelValues = new ContentValues();
                    levelValues.put(KEY_LEVEL_ID, level.getId());
                    levelValues.put(KEY_LEVEL_NUM, level.getLevelNum());
                    levelValues.put(KEY_LEVEL_NAME, level.getLevelName());
                    levelValues.put(KEY_POINTS_FOR_NEXT_LEVEL, level.getPointsNeededForThisLevel());
                    levelValues.put(KEY_QUESTION_LENGTH, level.getQuestionLength());

                    db.insert(TABLE_LEVEL, null, levelValues);
                }

            }


        /*
         * This method handles the case of changes to the structure of the database
         */
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // on upgrade drop older tables
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVEL);

                // create new tables
                onCreate(db);
            }
        }


}


