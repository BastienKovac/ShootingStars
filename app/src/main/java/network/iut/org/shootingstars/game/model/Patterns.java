package network.iut.org.shootingstars.game.model;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

/**
 * Created by Android on 16/03/2017.
 */

public class Patterns {

    private static Patterns instance;

    private List<Deque<PointF>> trajectories;
    private Deque<PointF> bossTrajectory;
    private int width, height;

    private Patterns() {
        this.trajectories = new ArrayList<>();
    }

    public static Patterns getInstance() {
        if (instance == null) {
            instance = new Patterns();
        }
        return instance;
    }

    public void buildPatterns(int width, int height) {
        this.width = width;
        this.height = height;
        for (int i = 0 ; i < 20 ; i++) {
            buildSinusPattern((float) i);
        }
        buildBossPattern();
    }

    public Deque<PointF> getBossPattern() {
        return ((ArrayDeque<PointF>) bossTrajectory).clone();
    }

    public Deque<PointF> getRandomPattern() {
        if (trajectories.size() == 1) {
            return trajectories.get(0);
        } else if (trajectories.size() > 1) {
            Random r = new Random();
            int index = r.nextInt(trajectories.size());
            return trajectories.get(index);
        } else {
            return new ArrayDeque<>();
        }
    }

    private void buildSinusPattern(float offset) {
        Deque<PointF> trajectory = new ArrayDeque<>();
        for (float y = 0 ; y < height ; y += 5f) {
            float x = getSinusPoint((float) y, offset);
            PointF p = new PointF(x, y);
            trajectory.add(p);
        }
        trajectories.add(trajectory);
    }

    private float getSinusPoint(float y, float offset) {
        return ((width / 2f) * (float) Math.sin(((0.010f * y) - offset) % (2 * Math.PI)) + (width / 2f));
    }

    private void buildBossPattern() {
        bossTrajectory = new ArrayDeque<>();
        float w = width / 2;
        float h;
        for (h = height * -0.1f ; h < height * 0.12f ; h+= 0.5f) {
            PointF p = new PointF(w, h);
            bossTrajectory.add(p);
        }
    }

}
