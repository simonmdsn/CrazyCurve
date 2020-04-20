package sdu.cbse.group2.mapeditor.obstacles;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.services.ObstacleService;

import java.util.concurrent.ThreadLocalRandom;

//TODO different water textures?
public class Water implements ObstacleService {

    private final String[] waterGamespriteStrings = new String[]{
            "textures/obstacles/water/water.png",
    };

    @Override
    public Entity create(float x, float y) {
        return new WaterObstacle(new GameSprite(getWaterGamespriteString(), 30, 30), x, y);
    }

    @Override
    public String getObstacleName() {
        return "Water";
    }

    private String getWaterGamespriteString() {
        return waterGamespriteStrings[ThreadLocalRandom.current().nextInt(waterGamespriteStrings.length)];
    }

    private static class WaterObstacle extends Obstacle {

        public WaterObstacle(GameSprite gameSprite, float x, float y) {
            super(gameSprite, x, y);
        }
    }


}
