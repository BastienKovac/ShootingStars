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

    private static final int REF_FREQ_SPAWN = 20;
    private static final int REF_FREQ_PATTERN = REF_FREQ_SPAWN * 1;
    private static final int REF_FREQ_PLAYER_SHOOTING = REF_FREQ_SPAWN;
    private static final int REF_FREQ_ENEMY_SHOOTING = REF_FREQ_PLAYER_SHOOTING * 4;

    private EntityManager entityManager;
    private int frequencySpawn = 0;
    private int frequencyPattern = REF_FREQ_PATTERN;
    private int playerShotFreq = REF_FREQ_PLAYER_SHOOTING;

    private Deque<PointF> referenceTrajectory;


    public GameModel() {
        entityManager = new EntityManager();
        referenceTrajectory = Patterns.getInstance().getRandomPattern();
    }

    public void reinitialize(Context context) {
        frequencySpawn = 0;
        frequencyPattern = REF_FREQ_PATTERN;
        playerShotFreq = REF_FREQ_PLAYER_SHOOTING;
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
        if (frequencyPattern == REF_FREQ_PATTERN) {
            referenceTrajectory = Patterns.getInstance().getRandomPattern();
            frequencyPattern = 0;
        } else {
            frequencyPattern++;
        }
    }

    private void updateSpawn(Context context) {
        if (frequencySpawn == REF_FREQ_SPAWN) {
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
        entityManager.makeEnemiesShoot(context, REF_FREQ_ENEMY_SHOOTING);
        if (playerShotFreq == REF_FREQ_PLAYER_SHOOTING) {
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
