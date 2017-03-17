package network.iut.org.flappydragon.game.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import network.iut.org.flappydragon.R;
import network.iut.org.flappydragon.game.GameActivity;

public class GameMenu extends Activity {

    private ImageView playerShip ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

    }

    public void btnEasyMode(View v){
        Intent gameActyvity = new Intent(this, GameActivity.class);
        startActivity(gameActyvity);
    }

    public void btnHardMode(View v){
        Intent gameActyvity = new Intent(this, GameActivity.class);
        startActivity(gameActyvity);
    }

    public void btnImpMode(View v){
        Intent gameActyvity = new Intent(this, GameActivity.class);
        startActivity(gameActyvity);
    }

    public void btnOptions(View v){
        Intent gameActyvity = new Intent(this, GameActivity.class);
        startActivity(gameActyvity);
    }

    public void btnCredits(View v){
        Intent gameActyvity = new Intent(this, GameActivity.class);
        startActivity(gameActyvity);
    }

    public void btnExit(View v){
        Intent gameActyvity = new Intent(this, GameActivity.class);
        startActivity(gameActyvity);
    }


}
