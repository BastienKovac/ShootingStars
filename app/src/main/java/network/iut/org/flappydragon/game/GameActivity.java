package network.iut.org.flappydragon.game;

import android.graphics.Point;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

import network.iut.org.flappydragon.entities.AbstractEntity;
import network.iut.org.flappydragon.entities.CircleEnemyShip;
import network.iut.org.flappydragon.entities.FrameHolder;
import network.iut.org.flappydragon.entities.PlayerShip;
import network.iut.org.flappydragon.game.model.GameModel;
import network.iut.org.flappydragon.game.model.Patterns;
import network.iut.org.flappydragon.game.view.GameView;
import network.iut.org.flappydragon.game.view.background.Background;
import network.iut.org.flappydragon.util.SoundPoolUtil;

public class GameActivity extends AppCompatActivity {

    private GameView view;
    private GameModel model;

    private int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoundPoolUtil.init(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        view = new GameView(this);
        model = new GameModel();
        view.setModel(model);
        setContentView(view);
        SoundPoolUtil.getInstance().startBackgroundMusic();
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
        view.addBackground(new Background(this, width, height, "layer_0", 100));
        view.addBackground(new Background(this, width, height, "layer_1", 125));
        view.addBackground(new Background(this, width, height, "layer_2", 300));
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
