package network.iut.org.shootingstars.game;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import network.iut.org.shootingstars.entities.FrameHolder;
import network.iut.org.shootingstars.entities.PlayerShip;
import network.iut.org.shootingstars.game.model.GameModel;
import network.iut.org.shootingstars.game.model.Patterns;
import network.iut.org.shootingstars.game.view.GameView;

public class GameActivity extends AppCompatActivity {

    private GameView view;
    private GameModel model;

    private int difficultyMode; // 1 = easy, 2 = hard, 3 = impossible

    private int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        difficultyMode = getIntent().getIntExtra("Difficulty", 1);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        view = new GameView(this);
        model = new GameModel(difficultyMode);
        view.setModel(model);
        setContentView(view);
        initDimensions();
        Patterns.getInstance().buildPatterns(width, height);
        FrameHolder.getInstance().initExplosionsFrame(this);
        FrameHolder.getInstance().initPlayerShot(this);
        initBackgrounds();
        initSpawnPoints();
    }

    private void initDimensions() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }

    private void initBackgrounds() {
        FrameHolder.getInstance().initBackgrounds(this, width, height);
    }

    private void initSpawnPoints() {
        initAlly(width, height);
    }

    private void initAlly(int w, int h) {
        PlayerShip ally = new PlayerShip(this);
        ally.setX(w / 2);
        ally.setY(h * 0.75f);
        model.getEntityManager().setPlayerEntity(ally);
    }

}
