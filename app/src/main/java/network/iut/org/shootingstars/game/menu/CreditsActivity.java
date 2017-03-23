package network.iut.org.shootingstars.game.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import network.iut.org.shootingstars.R;
import network.iut.org.shootingstars.util.SoundPoolUtil;

public class CreditsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        TextView text = (TextView)findViewById(R.id.textcredits);
        String html = "<H1>Developement</H1>" +
                "<br/>" +
                "<p>KOVAC Bastien <br/> VIDAL Olivier</p>" +
                "<br/>" +
                "<H1>Game Design</H1>" +
                "<br/>" +
                "<p>VIDAL Olivier <br/> KOVAC Bastien</p>" +
                "<br/>" +
                "<H1>Sound Design</H1>" +
                "<br/>" +
                "<p>VIDAL Olivier <br/>" +
                "KOVAC Bastien <br/>" +
                "Analog synthesizer ARP 2600</p>";
        text.setText(Html.fromHtml(html));
    }

    public void backButton(View v) {
        Intent gameActivity = new Intent(this, GameMenu.class);
        SoundPoolUtil.getInstance().playBackBtn();
        startActivity(gameActivity);
        finish();
    }

}
