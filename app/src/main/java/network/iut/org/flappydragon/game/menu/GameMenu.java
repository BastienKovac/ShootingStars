package network.iut.org.flappydragon.game.menu;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;

import network.iut.org.flappydragon.R;

public class GameMenu extends Activity {

    private ImageView playerShip ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

    }
}
