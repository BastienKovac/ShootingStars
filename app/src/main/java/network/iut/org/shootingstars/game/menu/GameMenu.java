package network.iut.org.shootingstars.game.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import network.iut.org.shootingstars.R;
import network.iut.org.shootingstars.game.GameActivity;
import network.iut.org.shootingstars.game.OptionsActivity;
import network.iut.org.shootingstars.util.PreferencesUtil;
import network.iut.org.shootingstars.util.SoundPoolUtil;

public class GameMenu extends Activity {

    private ImageView playerShip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferencesUtil.loadPreferences(this);
        SoundPoolUtil.init(this);
        SoundPoolUtil.getInstance().startBackgroundMusic();
        setContentView(R.layout.activity_game_menu);
    }

    public void btnEasyMode(View v){
        Intent gameActivity = new Intent(this, GameActivity.class);
        gameActivity.putExtra("Difficulty", 1);
        startActivity(gameActivity);
        finish();
    }

    public void btnHardMode(View v){
        Intent gameActivity = new Intent(this, GameActivity.class);
        gameActivity.putExtra("Difficulty", 2);
        startActivity(gameActivity);
        finish();
    }

    public void btnImpMode(View v){
        Intent gameActivity = new Intent(this, GameActivity.class);
        gameActivity.putExtra("Difficulty", 3);
        startActivity(gameActivity);
        finish();
    }

    public void btnOptions(View v){
        Intent gameActivity = new Intent(this, OptionsActivity.class);
        startActivity(gameActivity);
        finish();
    }

    public void btnCredits(View v){
        Intent gameActivity = new Intent(this, GameActivity.class);
        startActivity(gameActivity);
        finish();
    }

}
