package sdu.cbse.group2.mapeditor.obstacles;

import sdu.cbse.group2.common.data.GameSprite;

import java.util.concurrent.ThreadLocalRandom;

public class Water extends Obstacle  {

    private String[] waterGamespriteStrings = new String[]{
            "/obstacles/water/water1.png",
            "/obstacles/water/water2.png",
            "/obstacles/water/water3.png",
    };

    public Water(GameSprite gameSprite, float x, float y) {
        super(gameSprite, x, y);
    }

    private String getWaterGamespriteString() {
        return waterGamespriteStrings[ThreadLocalRandom.current().nextInt(waterGamespriteStrings.length)];
    }

    public Water create(float x, float y) {
        return new Water(new GameSprite(getWaterGamespriteString(), 30, 30), x, y);
    }
}
