package sdu.cbse.group2.mapeditor.obstacles;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.services.ObstacleService;
import sdu.cbse.group2.commonobstacle.CommonObstacle;

public class Lava implements ObstacleService {

    @Override
    public Entity create(float x, float y) {
        return new LavaObstacle(getGameSprite(), x, y);
    }

    @Override
    public GameSprite getGameSprite() {
        return new GameSprite("textures/obstacles/lava/lava.png", 30, 30);
    }

    @Override
    public String getObstacleName() {
        return "Lava";
    }

    private class LavaObstacle extends CommonObstacle {
        public LavaObstacle(GameSprite gameSprite, float x, float y) {
            super(gameSprite, x, y);
        }
    }
}
