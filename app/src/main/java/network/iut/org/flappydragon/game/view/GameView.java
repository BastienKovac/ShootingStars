package network.iut.org.flappydragon.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

import network.iut.org.flappydragon.Background;
import network.iut.org.flappydragon.entities.CircleEnemyShip;
import network.iut.org.flappydragon.entities.PlayerShip;
import network.iut.org.flappydragon.game.model.EntityManager;

public class GameView extends SurfaceView implements Runnable {

    private SurfaceHolder holder;
    private boolean paused = true;

    private EntityManager entityManager;

    public static final long FPS = 60;

    private Timer timer = new Timer();
    private TimerTask timerTask;
    private Background background;

    private float originX, originY;

    public GameView(Context context) {
        super(context);
        background = new Background(context, this);
        holder = getHolder();
        int w = getWidth();
        int h = getHeight();
        this.originX = w / 2f;
        this.originY = h * 0.9f;

        entityManager = new EntityManager(new PlayerShip(context, this));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            if(paused) {
                resume();
            } else {
                this.entityManager.getPlayerEntity().moveTo(event.getX(), event.getY());
            }
        }
        return true;
    }

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    private void resume() {
        paused = false;
        startTimer();
    }

    private void startTimer() {
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, 1000 / FPS, 1000 / FPS);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private void setUpTimerTask() {
        stopTimer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                GameView.this.run();
            }
        };
    }

    @Override
    public void run() {
        draw();
    }

    private void draw() {
        while(!holder.getSurface().isValid()) {
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Canvas canvas = holder.lockCanvas();
        drawCanvas(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    private void drawCanvas(Canvas canvas) {
        background.draw(canvas);
        entityManager.draw(canvas);
    }

}
