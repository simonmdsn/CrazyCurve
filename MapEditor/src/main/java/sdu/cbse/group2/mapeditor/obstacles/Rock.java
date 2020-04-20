package sdu.cbse.group2.mapeditor.obstacles;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.services.ObstacleService;

public class Rock implements ObstacleService {

    private String gamespriteString = "textures/obstacles/rock/rock.png";

    @Override
    public Entity create(float x, float y) {
        return new RockObstacle(new GameSprite(gamespriteString, 30, 30), x, y);
    }

    @Override
    public String getObstacleName() {
        return "Rock";
    }

    private class RockObstacle extends Obstacle {

        public RockObstacle(GameSprite gameSprite, float x, float y) {
            super(gameSprite, x, y);
        }
    }
}
