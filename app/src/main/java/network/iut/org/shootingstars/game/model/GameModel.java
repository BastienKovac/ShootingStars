package network.iut.org.shootingstars.game.model;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;

import network.iut.org.shootingstars.entities.AbstractEntity;
import network.iut.org.shootingstars.entities.CircleEnemyShip;
import network.iut.org.shootingstars.game.view.GameView;

/**
 * Created by Android on 13/03/2017.
 */

public class GameModel {

    private static final int BOSS_SPAWN_IN_SECONDS = 1;

    private int age;
    private boolean shouldSpawnBoss;

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
        this.difficultyMode = difficultyMode;
        entityManager = new EntityManager(difficultyMode);
        referenceTrajectory = Patterns.getInstance().getRandomPattern();
        initDifficulty();
    }

    private void initDifficulty() {
        this.refFreqSpawn = 30 / difficultyMode;
        this.refFreqPatterns = refFreqSpawn * (3 / difficultyMode);
        this.refFreqPlayerShooting = refFreqSpawn / (3 / difficultyMode);
        this.refFreqEnemyShooting = refFreqPlayerShooting * (10 / difficultyMode);

        this.frequencyPattern = refFreqPatterns;
        this.playerShotFreq = refFreqPlayerShooting;
    }

    public void reinitialize(Context context) {
        frequencySpawn = 0;
        age = 0;
        shouldSpawnBoss = false;
        frequencyPattern = refFreqPatterns;
        playerShotFreq = refFreqPlayerShooting;
        entityManager.reinitialize(context);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean updateStatus(Context context) {
        age++;
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

    public int getDifficultyMode() {
        return difficultyMode;
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
        if (!shouldSpawnBoss && age / GameView.FPS == BOSS_SPAWN_IN_SECONDS && entityManager.getPlayerEntity() != null) {
            shouldSpawnBoss = true;
        } else if (!shouldSpawnBoss && frequencySpawn == refFreqSpawn) {
            AbstractEntity enemy = new CircleEnemyShip(context);
            if (referenceTrajectory != null) {
                enemy.setTrajectory(((ArrayDeque) referenceTrajectory).clone());
            }
            entityManager.addEnemyEntity(enemy);
            frequencySpawn = 0;
        } else if (!shouldSpawnBoss) {
            frequencySpawn++;
        } else if (entityManager.isEnemyEmpty()) {
            entityManager.spawnBoss(context);
            shouldSpawnBoss = false;
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
