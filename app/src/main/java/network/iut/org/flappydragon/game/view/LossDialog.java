package network.iut.org.flappydragon.game.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import network.iut.org.flappydragon.R;
import network.iut.org.flappydragon.game.GameActivity;
import network.iut.org.flappydragon.game.menu.GameMenu;

/**
 * Created by Android on 17/03/2017.
 */

public class LossDialog extends Dialog implements View.OnClickListener {

    private Button yes, no;
    private GameView associatedView;


    public LossDialog(GameView associatedView) {
        super(associatedView.getContext());
        setCanceledOnTouchOutside(false);
        this.associatedView = associatedView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loss_dialog_layout);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                associatedView.reinitialize();
                dismiss();
                break;
            case R.id.btn_no:
                Intent i = new Intent(associatedView.getContext(), GameMenu.class);
                associatedView.getContext().startActivity(i);
                ((Activity) associatedView.getContext()).finish();
                dismiss();
                break;
            default:
                break;
        }
    }
}
