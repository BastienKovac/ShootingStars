package network.iut.org.flappydragon.game;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import network.iut.org.flappydragon.entities.PlayerShip;
import network.iut.org.flappydragon.game.model.GameModel;
import network.iut.org.flappydragon.game.view.GameView;
import network.iut.org.flappydragon.game.view.background.Background;

public class GameActivity extends AppCompatActivity {

    private GameView view;
    private GameModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GameView(this);
        model = new GameModel(this);
        view.setModel(model);
        setContentView(view);
        initBackgrounds();
        initSpawnPoints();
    }

    private void initBackgrounds() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        view.addBackground(new Background(this, width, height, "layer_0", 100));
        view.addBackground(new Background(this, width, height, "layer_1", 135));
    }

    private void initSpawnPoints() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        initAlly(width, height);
    }

    private void initAlly(int w, int h) {
        PlayerShip ally = new PlayerShip(this);
        ally.setX(w / 2);
        ally.setY(h * 0.75f);
        model.getEntityManager().setPlayerEntity(ally);
    }

}
