package network.iut.org.flappydragon.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import network.iut.org.flappydragon.game.view.background.Background;
import network.iut.org.flappydragon.game.model.GameModel;

public class GameView extends SurfaceView implements Runnable {

    private static final long FPS = 60;

    private List<Background> backgrounds;
    private SurfaceHolder holder;
    private GameModel model;

    private Timer timer = new Timer();
    private TimerTask timerTask;


    public GameView(Context context) {
        super(context);
        holder = getHolder();
        backgrounds = new ArrayList<>();
        resume();
    }

    public void setModel(GameModel model) {
        this.model = model;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            if (model.getEntityManager().getPlayerEntity() != null) {
                model.getEntityManager().getPlayerEntity().moveTo(event.getX(), event.getY());
            }
        }
        return true;
    }

    public void resume() {
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
        if (model != null) {
            model.updateStatus(getContext());
        }
        draw();
    }

    public void addBackground(Background bg) {
        backgrounds.add(bg);
    }

    private void draw() {
        while(!holder.getSurface().isValid()) {
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Canvas canvas = holder.lockCanvas();
        drawCanvas(canvas);
        try {
            holder.unlockCanvasAndPost(canvas);
        } catch (Exception ignored) {

        }
    }

    private void drawCanvas(Canvas canvas) {
        for (Background bg : backgrounds) {
            bg.update(FPS);
            drawBackground(canvas, bg);
        }
        model.getEntityManager().draw(canvas);
    }

    private void drawBackground(Canvas canvas, Background bg) {
        if (canvas == null) {
            return;
        }
        Rect fromRect1 = new Rect(0, 0, bg.getWidth(), bg.getHeight() - bg.getyClip());
        Rect toRect1 = new Rect(0, bg.getyClip(), bg.getWidth(), bg.getHeight());

        Rect fromRect2 = new Rect(0, bg.getHeight() - bg.getyClip(), bg.getWidth(), bg.getHeight());
        Rect toRect2 = new Rect(0, 0, bg.getWidth(), bg.getyClip());

        //draw the two background bitmaps
        if (!bg.isReversedFirst()) {
            canvas.drawBitmap(bg.getBg(), fromRect1, toRect1, null);
            canvas.drawBitmap(bg.getBgReversed(), fromRect2, toRect2, null);
        } else {
            canvas.drawBitmap(bg.getBg(), fromRect2, toRect2, null);
            canvas.drawBitmap(bg.getBgReversed(), fromRect1, toRect1, null);
        }
    }

}
