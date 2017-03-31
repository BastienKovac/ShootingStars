package network.iut.org.shootingstars.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Android on 31/03/2017.
 */

public class HighScoreDAO {

    // Champs de la base de données
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_DATE, DatabaseHelper.COLUMN_SCORE };

    public HighScoreDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public HighScore createHighScore(int score) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SCORE, score);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        sdf.setTimeZone(TimeZone.getDefault());
        Calendar cal = Calendar.getInstance();
        String currentDateAndTime = sdf.format(cal.getTime());
        values.put(DatabaseHelper.COLUMN_DATE, currentDateAndTime);

        long insertId = database.insert(DatabaseHelper.TABLE_SCORES, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_SCORES,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null, null);
        cursor.moveToFirst();
        HighScore newScore = cursorToHighScore(cursor);
        cursor.close();
        return newScore;
    }

    public List<HighScore> getAllScores() {
        List<HighScore> scores = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_SCORES,
                allColumns, null, null,
                null, null, DatabaseHelper.COLUMN_SCORE + " desc", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HighScore score = cursorToHighScore(cursor);
            scores.add(score);
            cursor.moveToNext();
        }
        cursor.close();
        return scores;
    }

    private HighScore cursorToHighScore(Cursor cursor) {
        HighScore score = new HighScore();
        score.setId(cursor.getLong(0));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        sdf.setTimeZone(TimeZone.getDefault());
        try {
            score.setDate(sdf.parse(cursor.getString(1)));
        } catch (ParseException e) {
            score.setDate(null);
        }
        score.setScore(cursor.getInt(2));
        return score;
    }


}
