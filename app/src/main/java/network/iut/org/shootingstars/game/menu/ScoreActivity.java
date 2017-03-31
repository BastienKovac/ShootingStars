package network.iut.org.shootingstars.game.menu;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.List;

import network.iut.org.shootingstars.R;
import network.iut.org.shootingstars.persistence.HighScore;
import network.iut.org.shootingstars.persistence.HighScoreDAO;
import network.iut.org.shootingstars.util.SoundPoolUtil;

public class ScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        HighScoreDAO dao = new HighScoreDAO(this);
        dao.open();
        List<HighScore> scores = dao.getAllScores();
        dao.close();

        ListView listView = (ListView) findViewById(R.id.listScores);
        String[] scoreDisplay = new String[scores.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'at' HH'h'mm");
        for (int i = 0 ; i < scoreDisplay.length && i < 10 ; i++) {
            scoreDisplay[i] = (i + 1) + " -\nScore obtained the " + sdf.format(scores.get(i).getDate())
                            + "\nHigh Score : " + scores.get(i).getScore() + "\n";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_score_item, R.id.scoreView, scoreDisplay);
        listView.setAdapter(adapter);
    }

    public void backButton(View v) {
        Intent gameActivity = new Intent(this, GameMenu.class);
        SoundPoolUtil.getInstance().playBackBtn();
        startActivity(gameActivity);
        finish();
    }
}
