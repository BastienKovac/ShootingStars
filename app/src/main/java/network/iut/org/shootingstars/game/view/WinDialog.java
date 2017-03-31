package network.iut.org.shootingstars.game.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import network.iut.org.shootingstars.R;
import network.iut.org.shootingstars.game.menu.GameMenu;
import network.iut.org.shootingstars.persistence.HighScoreDAO;

/**
 * Created by Android on 28/03/2017.
 */

public class WinDialog extends Dialog implements View.OnClickListener {

    private Button menu, retry;
    private GameView associatedView;

    public WinDialog(GameView associatedView) {
        super(associatedView.getContext());
        setCanceledOnTouchOutside(false);
        this.associatedView = associatedView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.win_dialog_layout);
        menu = (Button) findViewById(R.id.btn_menu);
        retry = (Button) findViewById(R.id.btn_retry);
        menu.setOnClickListener(this);
        retry.setOnClickListener(this);
        TextView score = (TextView) findViewById(R.id.score);
        score.setText("Score : " + associatedView.getScore());
        HighScoreDAO dao = new HighScoreDAO(associatedView.getContext());
        dao.open();
        dao.createHighScore(associatedView.getScore());
        dao.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_retry:
                associatedView.reinitialize();
                dismiss();
                break;
            case R.id.btn_menu:
                Intent i = new Intent(associatedView.getContext(), GameMenu.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                associatedView.getContext().startActivity(i);
                dismiss();
                return;
            default:
                break;
        }
    }


}
