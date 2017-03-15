package network.iut.org.flappydragon.game.model;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import network.iut.org.flappydragon.entities.AbstractEntity;

/**
 * Created by Android on 13/03/2017.
 */

public class EntityManager {

    private List<AbstractEntity> enemyEntities;
    private AbstractEntity playerEntity;

    private float allySpawnX, allySpawnY;


    public EntityManager() {
        this.enemyEntities = new ArrayList<>();
    }

    public void setPlayerEntity(AbstractEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void setAllySpawnX(float allySpawnX) {
        this.allySpawnX = allySpawnX;
    }

    public void setAllySpawnY(float allySpawnY) {
        this.allySpawnY = allySpawnY;
    }

    public void addEnemyEntity(AbstractEntity enemyEntity) {
        this.enemyEntities.add(enemyEntity);
    }

    public void removeEnemyEntity(AbstractEntity enemyEntity) {
        this.enemyEntities.remove(enemyEntity);
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            playerEntity.draw(canvas);
            for (AbstractEntity entity : enemyEntities) {
                entity.draw(canvas);
            }
        }
    }

    public AbstractEntity getPlayerEntity() {
        return playerEntity;
    }
}
