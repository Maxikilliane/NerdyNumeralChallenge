package de.mi.ur.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.LevelLogic.Level;

/**
 * Created by Anna-Marie on 09.08.2016.
 */
public class NNCDatabase implements HighscoreListener {
    private static final String DATABASE_NAME = "NNC Database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_HIGHSCORE = "nncGameHighscores";
    private static final String KEY_ID = "_id";
    private static final String KEY_RANK = "rank";
    private static final String KEY_POINTS = "points";
    private static final String KEY_NAME = "name";

    private static final String TABLE_LEVEL = "nncLevel";
    private static final String KEY_LEVEL_ID = "_id";
    private static final String KEY_LEVEL_NUM = "level_number";
    private static final String KEY_LEVEL_NAME = "level_name";
    private static final String KEY_POINTS_FOR_NEXT_LEVEL = "points_for_next_level";
    private static final String KEY_QUESTION_LENGTH = "question_length";


    private static final int COLUMN_RANK_INDEX = 1;
    private static final int COLUMN_POINTS_INDEX = 2;
    private static final int COLUMN_NAME_INDEX = 3;

    private static final int COLUMN_LEVEL_ID_INDEX = 0;
    private static final int COLUMN_LEVEL_NUM_INDEX = 1;
    private static final int COLUMN_LEVEL_NAME_INDEX = 2;
    private static final int COLUMN_POINTS_NEXT_LEVEL_INDEX = 3;
    private static final int COLUMN_QUESTION_LENGTH_INDEX = 4;

    private static final String[] ALL_COLUMNS_HIGHSCORE = {KEY_ID, KEY_RANK, KEY_POINTS, KEY_NAME};
    private static final String[] ALL_COLUMNS_LEVEL = {KEY_ID, KEY_LEVEL_NUM, KEY_LEVEL_NAME, KEY_POINTS_FOR_NEXT_LEVEL, KEY_QUESTION_LENGTH};

    private NncDBOpenHelper dbHelper;
    private SQLiteDatabase database;

    public NNCDatabase(Context context) {
        dbHelper = new NncDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

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


    public long insertHighscoreData(Highscore highscore) {
        ContentValues highscoreValues = new ContentValues();

        highscoreValues.put(KEY_RANK, highscore.getRank());
        highscoreValues.put(KEY_POINTS, highscore.getPoints());
        highscoreValues.put(KEY_NAME, highscore.getName());

        return database.insert(TABLE_HIGHSCORE, null, highscoreValues);
    }

    public void removeHighscoreData (int rank){
        String whereClause = KEY_RANK + " = "+ rank;
        database.delete(TABLE_HIGHSCORE, whereClause, null);
    }


    public Highscore getHighscoreWithCertainRank(int rank) {

        String whereClause = KEY_RANK + " = " + rank;
        Cursor cursor = database.query(TABLE_HIGHSCORE, ALL_COLUMNS_HIGHSCORE, whereClause, null, null, null, null);
        ArrayList<Highscore> highscore = buildHighscoresFromCursor(cursor);

        return highscore.get(0);
    }

    public ArrayList<Highscore> getAllHighscores() {
        Cursor cursor = getAllHighscoresCursor();
        return buildHighscoresFromCursor(cursor);
    }

    public Cursor getAllHighscoresCursor() {
        return database.query(TABLE_HIGHSCORE, ALL_COLUMNS_HIGHSCORE, null, null, null, null, KEY_RANK + " ASC");
    }

    //um duplicate code zu vermeiden wird das "Zusammenbauen" der Highscores in diese Methode ausgelagert
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

    public Level getCurrentLevel(){
        String whereClause = KEY_LEVEL_ID + " = " + 20;
        Cursor cursor = database.query(TABLE_LEVEL, ALL_COLUMNS_LEVEL, whereClause, null, null, null, null);
        return buildLevelFromCursor(cursor).get(0);
    }

    private void removeCurrentLevel(){
        String whereClause = KEY_LEVEL_ID + " = " + 20;
        database.delete(TABLE_LEVEL, whereClause, null);
    }
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

    public Level getLevel(int levelId) {
        if (levelId < 0 || levelId > 9) {
            levelId = 0;
        }
        String whereClause = KEY_LEVEL_ID + " = " + levelId;
        Cursor cursor = database.query(TABLE_LEVEL, ALL_COLUMNS_LEVEL, whereClause, null, null, null, null);
        return buildLevelFromCursor(cursor).get(0);
    }

    public boolean checkIfNextLevel(){
        Level currentLevel = getCurrentLevel();
        int currentLevelNum = currentLevel.getLevelNum();
        if(currentLevelNum < 9) {
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

    @Override
    public void saveHighscoreToDatabase(int rank, int points) {
        removeHighscoreData(rank);
        // Evtl im Spiel den Player zum Namen-Eingeben prompten und den dann noch übergeben.
        insertHighscoreData(new Highscore(rank, points, ""));

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

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_HIGHSCORE_TABLE);
                db.execSQL(CREATE_LEVEL_TABLE);

                Level[] levels = {new Level(0, 0, "Unwissender", 0, 0),
                        new Level(1, 1, "Initiant", 100, 0),
                        new Level(2, 2, "Padawan", 300, 0),
                        new Level(3, 3, "Nullen-Nerd", 600, 0),
                        new Level(4, 4, "edler Einsen-Verehrer", 1000, 1),
                        new Level(5, 5, "Quaternal-Kenner", 1500, 1),
                        new Level(6, 6, "Oktal-Jongleur", 2100, 1),
                        new Level(7, 7, "Hex-Beherrscher", 2800, 2),
                        new Level(8, 8, "Meister der Systeme", 3600, 2),
                        new Level(9, 9, "5up3r N3rd", 4500, 3),
                        new Level(20, 0, "Unwissender", 0, 0)
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


