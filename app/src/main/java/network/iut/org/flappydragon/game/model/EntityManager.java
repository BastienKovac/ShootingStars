package network.iut.org.flappydragon.game.model;

import java.util.ArrayList;
import java.util.List;

import network.iut.org.flappydragon.entities.AbstractEntity;

/**
 * Created by Android on 13/03/2017.
 */

public class EntityManager {

    private List<AbstractEntity> enemyEntities;
    private AbstractEntity playerEntity;


    public EntityManager(AbstractEntity playerEntity) {
        this.playerEntity = playerEntity;
        this.enemyEntities = new ArrayList<>();
    }

    public void addEnemyEntity(AbstractEntity enemyEntity) {
        this.enemyEntities.add(enemyEntity);
    }

    public void removeEnemyEntity(AbstractEntity enemyEntity) {
        this.enemyEntities.remove(enemyEntity);
    }

    public boolean updateGameState() {
        for (AbstractEntity enemy : enemyEntities) {
            if (playerEntity.collideWith(enemy.getX(), enemy.getY())) {
                return false;
            }
        }
        return true;
    }

}
