package network.iut.org.flappydragon.game.model;

import android.content.Context;

import network.iut.org.flappydragon.entities.PlayerShip;
import network.iut.org.flappydragon.game.view.GameView;

/**
 * Created by Android on 13/03/2017.
 */

public class GameModel {

    private EntityManager entityManager;


    public GameModel(Context context) {
        entityManager = new EntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
