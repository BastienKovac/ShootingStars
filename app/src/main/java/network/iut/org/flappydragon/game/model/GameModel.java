package network.iut.org.flappydragon.game.model;

import android.content.Context;
import android.graphics.PointF;

import java.util.ArrayDeque;
import java.util.Deque;

import network.iut.org.flappydragon.entities.AbstractEntity;
import network.iut.org.flappydragon.entities.CircleEnemyShip;

/**
 * Created by Android on 13/03/2017.
 */

public class GameModel {

    private int refFreqSpawn;
    private int refFreqPatterns;
    private int refFreqPlayerShooting;
    private int refFreqEnemyShooting;

    private EntityManager entityManager;
    private int frequencySpawn = 0;
    private int frequencyPattern;
    private int playerShotFreq;

    private Deque<PointF> referenceTrajectory;

    private int difficultyMode;


    public GameModel(int difficultyMode) {
        entityManager = new EntityManager();
        referenceTrajectory = Patterns.getInstance().getRandomPattern();
        this.difficultyMode = difficultyMode;
        initDifficulty();
    }

    private void initDifficulty() {
        this.refFreqSpawn = 30 / difficultyMode;
        this.refFreqPatterns = refFreqSpawn * (10 / difficultyMode);
        this.refFreqPlayerShooting = refFreqSpawn;
        this.refFreqEnemyShooting = refFreqPlayerShooting * (3 / difficultyMode);

        this.frequencyPattern = refFreqPatterns;
        this.playerShotFreq = refFreqPlayerShooting;
    }

    public void reinitialize(Context context) {
        frequencySpawn = 0;
        frequencyPattern = refFreqPatterns;
        playerShotFreq = refFreqPlayerShooting;
        entityManager.reinitialize(context);
    }

    public boolean updateStatus(Context context) {
        boolean status = true;
        updatePattern();
        updateSpawn(context);
        updateShots(context);
        updateCollisions();
        status = entityManager.updatePlayerStatus();
        updateExplosions();
        entityManager.followEnemyTrajectories();
        return status;
    }

    private void updateExplosions() {
        entityManager.updateExplosions();
    }

    private void updateCollisions() {
        entityManager.updateCollisions();
    }

    private void updatePattern() {
        if (frequencyPattern == refFreqPatterns) {
            referenceTrajectory = Patterns.getInstance().getRandomPattern();
            frequencyPattern = 0;
        } else {
            frequencyPattern++;
        }
    }

    private void updateSpawn(Context context) {
        if (frequencySpawn == refFreqSpawn) {
            AbstractEntity enemy = new CircleEnemyShip(context);
            if (referenceTrajectory != null) {
                enemy.setTrajectory(((ArrayDeque) referenceTrajectory).clone());
            }
            entityManager.addEnemyEntity(enemy);
            frequencySpawn = 0;
        } else {
            frequencySpawn++;
        }
    }

    private void updateShots(Context context) {
        entityManager.makeEnemiesShoot(context, refFreqEnemyShooting);
        if (playerShotFreq == refFreqPlayerShooting) {
            entityManager.addPlayerShot(context);
            playerShotFreq = 0;
        } else {
            playerShotFreq++;
        }
        entityManager.updateShots();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
