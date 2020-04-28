package sdu.cbse.group2.mapeditor.obstacles;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.services.ObstacleService;

//TODO different water textures?
public class Water implements ObstacleService {

    @Override
    public Entity create(float x, float y) {
        return new WaterObstacle(getGameSprite(), x, y);
    }

    @Override
    public GameSprite getGameSprite() {
        return new GameSprite("textures/obstacles/water/water.png", 30, 30);
    }

    @Override
    public String getObstacleName() {
        return "Water";
    }

    private static class WaterObstacle extends Obstacle {
        public WaterObstacle(GameSprite gameSprite, float x, float y) {
            super(gameSprite, x, y);
        }
    }
}
