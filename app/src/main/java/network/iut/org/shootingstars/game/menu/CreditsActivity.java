package network.iut.org.shootingstars.game.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import network.iut.org.shootingstars.R;
import network.iut.org.shootingstars.util.SoundPoolUtil;

public class CreditsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    public void backButton(View v) {
        Intent gameActivity = new Intent(this, GameMenu.class);
        SoundPoolUtil.getInstance().playBackBtn();
        startActivity(gameActivity);
        finish();
    }

}
