package network.iut.org.flappydragon.game.model;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Deque;

import network.iut.org.flappydragon.entities.AbstractEntity;
import network.iut.org.flappydragon.entities.CircleEnemyShip;
import network.iut.org.flappydragon.entities.PlayerShip;
import network.iut.org.flappydragon.game.view.GameView;

/**
 * Created by Android on 13/03/2017.
 */

public class GameModel {

    private static final int REF_FREQ = 20;

    private EntityManager entityManager;
    private int frequency = REF_FREQ;

    private Deque<PointF> referenceTrajectory;


    public GameModel(Context context) {
        entityManager = new EntityManager();
    }

    public void updateEnemies(Context context) {
        if (frequency == REF_FREQ) {
            AbstractEntity enemy = new CircleEnemyShip(context);
            if (referenceTrajectory != null) {
                enemy.setTrajectory(((ArrayDeque) referenceTrajectory).clone());
            }
            entityManager.addEnemyEntity(enemy);
            frequency = 0;
        } else {
            frequency++;
        }
        entityManager.followEnemyTrajectories();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setReferenceTrajectory(Deque<PointF> referenceTrajectory) {
        this.referenceTrajectory = referenceTrajectory;
    }
}
