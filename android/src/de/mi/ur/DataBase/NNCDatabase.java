package de.mi.ur.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import de.mi.ur.AndroidCommunication.HighscoreListener;

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

    private static final String TABLE_LEVEL = "nnc _level";


    private static final int COLUMN_RANK_INDEX = 1;
    private static final int COLUMN_POINTS_INDEX = 2;
    private static final int COLUMN_NAME_INDEX = 3;


    private static final String[] ALL_COLUMNS_HIGHSCORE = {KEY_ID, KEY_RANK, KEY_POINTS, KEY_NAME};

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

    /*
    *returns -1 if not better than current highscores, else the rank to overwrite
    *in activity: nach Spielende checken, ob neuer Highscore, in dem Fall: Toast (Name eingeben Aufforderung), dann damit einen neuen Highscore machen
    * und einfügen, falls nichts passiert nichts
    *
    *
    * Jetzt als Interface-Methode verwendet!
    */
    /*public int checkIfNewHighscore(int points){
        if(getHighscoreWithCertainRank(1).getPoints() < points){
            return 1;
        }else if(getHighscoreWithCertainRank(2).getPoints() < points){
            return 2;
        }else if(getHighscoreWithCertainRank(3).getPoints() < points){
            return 3;
        }else{
            return -1;
        }
    }
    */

    public Highscore getHighscoreWithCertainRank(int rank) {

        String whereClause = KEY_RANK + " = " + rank;
        Cursor cursor = database.query(TABLE_HIGHSCORE, ALL_COLUMNS_HIGHSCORE, whereClause, null, null, null, null);
        ArrayList<Highscore> highscore = buildHighscoresFromCursor(cursor);

        return highscore.get(0);
    }

    public ArrayList<Highscore> getAllHighscores() {
        ArrayList<Highscore> highscores = new ArrayList<>();
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
            private static final String INTEGER_NOT_NULL = " integer not null, ";

            public static final String CREATE_HIGHSCORE_TABLE = "create table " + TABLE_HIGHSCORE
                    + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_RANK + INTEGER_NOT_NULL
                    + KEY_POINTS + INTEGER_NOT_NULL + KEY_NAME + " text);";

            public NncDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_HIGHSCORE_TABLE);
                // weitere ähnliche Zeilen für weitere Tabellen

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        }
    }


