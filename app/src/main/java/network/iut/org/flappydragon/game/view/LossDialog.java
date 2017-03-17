package network.iut.org.flappydragon.game.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import network.iut.org.flappydragon.R;

/**
 * Created by Android on 17/03/2017.
 */

public class LossDialog extends Dialog implements View.OnClickListener {

    private Button yes, no;
    private GameView associatedView;


    public LossDialog(GameView associatedView) {
        super(associatedView.getContext());
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
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
