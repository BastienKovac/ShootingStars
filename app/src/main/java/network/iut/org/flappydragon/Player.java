package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import network.iut.org.flappydragon.game.view.GameView;
import network.iut.org.flappydragon.util.GraphicsUtil;

public class Player {
    /** Static bitmap to reduce memory usage. */
    public static Bitmap[] globalBitmaps;
    private Bitmap bitmap;
    private final byte frameTime;
    private int frameTimeCounter;
    private final int width;
    private final int height;
    private float x;
    private float y;
    private float speedX;
    private float speedY;
    private GameView view;

    private int currentFrame;

    private float targetX, targetY;
    private float ratioSpeed;


    public Player(Context context, GameView view) {
        if(globalBitmaps == null) {
            globalBitmaps = new Bitmap[4];
        }
        globalBitmaps[0] = GraphicsUtil.getScaledBitmapAlpha8(context, R.drawable.player_ship_frame1);
        globalBitmaps[1] = GraphicsUtil.getScaledBitmapAlpha8(context, R.drawable.player_ship_frame2);
        globalBitmaps[2] = GraphicsUtil.getScaledBitmapAlpha8(context, R.drawable.player_ship_frame3);
        globalBitmaps[3] = GraphicsUtil.getScaledBitmapAlpha8(context, R.drawable.player_ship_frame4);
        this.currentFrame = 0;
        this.bitmap = globalBitmaps[0];
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        this.frameTime = 5;		// the frame will change every 3 runs
        this.y = context.getResources().getDisplayMetrics().heightPixels / 2;	// Startposition in the middle of the screen

        this.view = view;
        this.x = this.view.getWidth() / 6;
        this.speedX = 0;
        this.ratioSpeed = 0.1f;

        this.targetX = x;
        this.targetY = y;
    }

    public void setTargetCoordinates(float x, float y) {
        this.targetX = x;
        this.targetY = y;
    }

    public void stopMovement() {
        this.targetX = this.x;
        this.targetY = this.y;
    }

    private float getPosTabIncrease() {
        return - view.getHeight() / 100;
    }

    private float getTabSpeed() {
        return -view.getHeight() / 16f;
    }

    public void move() {
        changeToNextFrame();

    /*    if(speedY < 0){
            // The character is moving up
            Log.i("Move", "Moving up");
            speedY = speedY * 2 / 3 + getSpeedTimeDecrease() / 2;
        }else{
            // the character is moving down
            Log.i("Move", "Moving down");
            this.speedY += getSpeedTimeDecrease();
        }
        if(this.speedY > getMaxSpeed()){
            // speed limit
            this.speedY = getMaxSpeed();
        }
    */
        // manage frames
/*        if(row != 3){
            // not dead
            if(speedY > getTabSpeed() / 3 && speedY < getMaxSpeed() * 1/3){
                row = 0;
            }else if(speedY > 0){
                row = 1;
            }else{
                row = 2;
            }
        }
*/

        float diffX = this.targetX - this.x;
        float diffY = this.targetY - this.y;

        this.x += diffX * ratioSpeed;
        this.y += diffY * ratioSpeed;
    }

    protected void changeToNextFrame(){
        this.frameTimeCounter++;
        if(this.frameTimeCounter >= this.frameTime){
            this.currentFrame++;
            if (this.currentFrame == 4)
                this.currentFrame = 0;
            this.bitmap = globalBitmaps[currentFrame];
            this.frameTimeCounter = 0;
        }
    }

    private float getSpeedTimeDecrease() {
        return view.getHeight() / 320;
    }

    private float getMaxSpeed() {
        return view.getHeight() / 51.2f;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y , null);
    }
}
