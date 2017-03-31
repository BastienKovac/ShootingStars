package network.iut.org.shootingstars.persistence;

import java.util.Date;

/**
 * Created by Android on 31/03/2017.
 */

public class HighScore {

    private long id;
    private Date date;
    private int score;


    public HighScore(Date date, int score) {
        this.date = date;
        this.score = score;
    }

    public HighScore() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return id + " : Date = " + date + ", Score = " + score;
    }
}
