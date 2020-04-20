package sdu.cbse.group2.mapeditor.obstacles;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.services.ObstacleService;

import java.io.Serializable;

public class Lava implements ObstacleService, Serializable {

    @Override
    public Entity create(float x, float y) {
        return new LavaObstacle(new GameSprite("textures/obstacles/lava/lava.png", 30, 30), x, y);
    }

    @Override
    public String getObstacleName() {
        return "Lava";
    }

    private class LavaObstacle extends Obstacle {
        public LavaObstacle(GameSprite gameSprite, float x, float y) {
            super(gameSprite, x, y);
        }
    }
}
