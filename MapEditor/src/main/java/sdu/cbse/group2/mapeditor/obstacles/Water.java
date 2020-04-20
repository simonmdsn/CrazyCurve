package sdu.cbse.group2.mapeditor.obstacles;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.services.ObstacleService;

import java.util.concurrent.ThreadLocalRandom;

public class Water implements ObstacleService {

    protected final String[] waterGamespriteStrings = new String[]{
            "obstacles/water/water.png",
    };

    @Override
    public Entity create(float x, float y) {
        return new WaterObstacle(new GameSprite(getWaterGamespriteString(), 30, 30), x, y);
    }

    public String getWaterGamespriteString() {
        return waterGamespriteStrings[ThreadLocalRandom.current().nextInt(waterGamespriteStrings.length)];
    }

    private class WaterObstacle extends Obstacle {

        public WaterObstacle(GameSprite gameSprite, float x, float y) {
            super(gameSprite, x, y);
        }
    }


}
