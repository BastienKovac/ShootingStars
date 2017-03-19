package network.iut.org.flappydragon.game.model;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import network.iut.org.flappydragon.entities.AbstractEntity;
import network.iut.org.flappydragon.entities.Explosion;
import network.iut.org.flappydragon.entities.PlayerShip;
import network.iut.org.flappydragon.entities.Shot;
import network.iut.org.flappydragon.util.SoundPoolUtil;

/**
 * Created by Android on 13/03/2017.
 */

public class EntityManager {

    private List<AbstractEntity> enemyEntities;
    private AbstractEntity playerEntity;

    private List<Shot> playerShots;
    private List<Shot> enemyShots;

    private List<Explosion> explosions;

    private List<AbstractEntity> toPurge;
    private List<Explosion> explosionsToPurge;

    private int score;


    public EntityManager() {
        this.enemyEntities = new ArrayList<>();
        this.toPurge = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.explosionsToPurge = new ArrayList<>();
        this.playerShots = new ArrayList<>();
        this.enemyShots = new ArrayList<>();
        this.score = 0;
    }

    public void reinitialize(Context context) {
        this.enemyEntities.clear();
        this.toPurge.clear();
        this.explosions.clear();
        this.explosionsToPurge.clear();
        this.playerShots.clear();
        this.enemyShots.clear();
        float w = context.getResources().getDisplayMetrics().widthPixels;
        float h = context.getResources().getDisplayMetrics().heightPixels;
        playerEntity = new PlayerShip(context);
        playerEntity.setX(w / 2);
        playerEntity.setY(h * 0.75f);
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setPlayerEntity(AbstractEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void addEnemyEntity(AbstractEntity enemyEntity) {
        this.enemyEntities.add(enemyEntity);
    }

    public void addPlayerShot(Context context) {
        if (playerEntity != null) {
            this.playerShots.add(playerEntity.shoot(context, playerEntity.getX(), 0));
            SoundPoolUtil.getInstance().playLaser();
        }
    }

    public void makeEnemiesShoot(Context context, int frequency) {
        if (playerEntity == null) {
            return;
        }
        for (AbstractEntity entity : enemyEntities) {
            if (entity.getAge() % frequency == 0) {
                this.enemyShots.add(entity.shoot(context, playerEntity.getX(), playerEntity.getY()));
            }
        }
    }

    private void explode(AbstractEntity entity) {
        explosions.add(new Explosion(entity));
    }

    public void updateCollisions() {
        for (AbstractEntity entity : enemyEntities) {
            for (AbstractEntity shot : playerShots) {
                if (shot.collideWith(entity)) {
                    score++;
                    toPurge.add(entity);
                    toPurge.add(shot);
                    explode(entity);
                }
            }
        }
        purge();
    }

    public boolean updatePlayerStatus() {
        if (playerEntity == null) {
            return false;
        }
        boolean status = true;
        for (AbstractEntity enemy : enemyEntities) {
            if (enemy.collideWith(playerEntity)) {
                status = false;
                toPurge.add(enemy);
                toPurge.add(playerEntity);
                explode(playerEntity);
                explode(enemy);
            }
        }
        for (Shot eShot : enemyShots) {
            if (eShot.collideWith(playerEntity)) {
                status = false;
                toPurge.add(playerEntity);
                toPurge.add(eShot);
                explode(playerEntity);
            }
        }
        if (!status) {
            playerEntity = null;
        }
        purge();
        return status;
    }

    public void followEnemyTrajectories() {
        for (AbstractEntity entity : enemyEntities) {
            if (!entity.followTrajectory()) {
                toPurge.add(entity);
            }
        }
        purge();
    }

    public void updateExplosions() {
        for (Explosion e : explosions) {
            if (e.isStarted()) {
                SoundPoolUtil.getInstance().playExplosion();
            }
            if (e.isDone()) {
                explosionsToPurge.add(e);
            }
        }
        for (Explosion e : explosionsToPurge) {
            explosions.remove(e);
        }
        explosionsToPurge.clear();
    }

    public void updateShots() {
        for (Shot shot : playerShots) {
            shot.moveShoot();
            if (shot.isOutside()) {
                toPurge.add(shot);
            }
        }
        for (Shot shot : enemyShots) {
            shot.moveShoot();
            if (shot.isOutside()) {
                toPurge.add(shot);
            }
        }
        purge();
    }

    private void purge() {
        for (AbstractEntity entity : toPurge) {
            enemyEntities.remove(entity);
            playerShots.remove(entity);
            enemyShots.remove(entity);
        }
        toPurge.clear();
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            if (playerEntity != null) {
                playerEntity.draw(canvas);
            }
            for (AbstractEntity entity : enemyEntities) {
                entity.draw(canvas);
            }
            for (Explosion e : explosions) {
                e.draw(canvas);
            }
            for (Shot shot : playerShots) {
                shot.draw(canvas);
            }
            for (Shot shot : enemyShots) {
                shot.draw(canvas);
            }
        }
    }

    public AbstractEntity getPlayerEntity() {
        return playerEntity;
    }
}
