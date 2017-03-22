package network.iut.org.shootingstars.game.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

import network.iut.org.shootingstars.entities.FrameHolder;
import network.iut.org.shootingstars.game.view.background.Background;
import network.iut.org.shootingstars.game.model.GameModel;

public class GameView extends SurfaceView implements Runnable {

    public static final long FPS = 30;
    private int frameFreq;

    private SurfaceHolder holder;
    private GameModel model;

    private Timer timer = new Timer();
    private TimerTask timerTask;

    private boolean dialogDisplayed = true;


    public GameView(Context context) {
        super(context);
        holder = getHolder();
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
            frameFreq++;
            if (!model.updateStatus(getContext())) {
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!dialogDisplayed) {
                            LossDialog dialog = new LossDialog(GameView.this);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.show();
                            dialogDisplayed = true;
                        }
                    }
                });
            } else {
                if (frameFreq == 60) {
                    frameFreq = 0;
                    model.getEntityManager().incrementScore();
                }
                dialogDisplayed = false;
            }
        }
        draw();
    }

    public void reinitialize() {
        model.reinitialize(getContext());
        resume();
    }

    private void draw() {
        while(!holder.getSurface().isValid()) {
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Canvas canvas = holder.lockCanvas();
        try {
            drawCanvas(canvas);
            Paint p = new Paint();
            p.setColor(Color.YELLOW);
            p.setTextSize(30);
            String txt = model.getEntityManager().getScore() + " (x" + model.getDifficultyMode() + ")";
            canvas.drawText(txt, 20, 40, p);
            holder.unlockCanvasAndPost(canvas);
        } catch (Exception ignored) {

        }
    }

    private void drawCanvas(Canvas canvas) {
        for (Background bg : FrameHolder.getInstance().getBackgrounds()) {
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
