package network.iut.org.shootingstars.game.model;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import network.iut.org.shootingstars.entities.AbstractEntity;
import network.iut.org.shootingstars.entities.Boss;
import network.iut.org.shootingstars.entities.Explosion;
import network.iut.org.shootingstars.entities.PlayerShip;
import network.iut.org.shootingstars.entities.Shot;
import network.iut.org.shootingstars.util.SoundPoolUtil;

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

    private Boss bossShip;

    private int score;
    private int multiplier;

    private boolean leftCannon;
    private boolean win;


    public EntityManager(int multiplier) {
        this.enemyEntities = new ArrayList<>();
        this.toPurge = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.explosionsToPurge = new ArrayList<>();
        this.playerShots = new ArrayList<>();
        this.enemyShots = new ArrayList<>();
        this.score = 0;
        this.multiplier = multiplier;
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
        this.bossShip = null;
        this.win = false;
    }

    public int getScore() {
        return score;
    }

    public void setPlayerEntity(AbstractEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void spawnBoss(Context context) {
        bossShip = new Boss(context);
        if (multiplier == 1) {
            bossShip.setHp(90);
        } else if (multiplier == 2) {
            bossShip.setHp(300);
        } else {
            bossShip.setHp(600);
        }
    }

    public int getBossHP() {
        return bossShip.getHp();
    }

    public boolean hasBoss() {
        return bossShip != null;
    }

    public boolean isEnemyEmpty() {
        return enemyEntities.isEmpty();
    }

    public void addEnemyEntity(AbstractEntity enemyEntity) {
        if (bossShip == null && !win) {
            this.enemyEntities.add(enemyEntity);
        }
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
        if (!win && bossShip != null) {
            if (bossShip.getAge() % (frequency / 10) == 0) {
                Shot[] shots = bossShip.shootWhips(context);
                for (Shot shot : shots) {
                    this.enemyShots.add(shot);
                }
            }
            if (bossShip.getAge() % frequency == 0) {
                if (leftCannon) {
                    this.enemyShots.add(bossShip.shootLeftCanon(context, playerEntity.getX(), playerEntity.getY()));
                    leftCannon = false;
                } else {
                    this.enemyShots.add(bossShip.shootRightCanon(context, playerEntity.getX(), playerEntity.getY()));
                    leftCannon = true;
                }
            }
        }
    }

    private void explode(AbstractEntity entity) {
        if (entity instanceof Boss) {
            Boss boss = (Boss) entity;
            for (int i = 0 ; i < 100 ; i++) {
                explosions.add(new Explosion(boss.getRandomExplosionPoint()));
            }
        } else {
            explosions.add(new Explosion(entity));
        }
    }

    public void updateCollisions() {
        for (AbstractEntity entity : enemyEntities) {
            for (AbstractEntity shot : playerShots) {
                if (shot.collideWith(entity)) {
                    score += (10 * multiplier);
                    toPurge.add(entity);
                    toPurge.add(shot);
                    explode(entity);
                }
            }
        }
        for (AbstractEntity shot : playerShots) {
            if (!win && bossShip != null && bossShip.collideWith(shot)) {
                bossShip.decrementHp();
                if (bossShip.isDestroyed()) {
                    explode(bossShip);
                    score += (1000 * multiplier);
                    win = true;
                }
                toPurge.add(shot);
                explode(shot);
            }
        }
        if (bossShip != null && win && explosions.isEmpty()) {
            bossShip = null;
        }
        purge();
    }

    public boolean isWin() {
        return win;
    }

    public void incrementScore() {
        if (!win) {
            this.score += multiplier;
        }
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
        if (bossShip != null && bossShip.collideWith(playerEntity)) {
            status = false;
            toPurge.add(playerEntity);
            explode(playerEntity);
        }
        if (!status) {
            SoundPoolUtil.getInstance().playR2D2();
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
        if (bossShip != null) {
            bossShip.followTrajectory();
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
            if (bossShip != null) {
                bossShip.draw(canvas);
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
