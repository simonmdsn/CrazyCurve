package sdu.cbse.group2.round;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.List;

public class Spawn {


    private PositionPart getEntityPositionPart(Entity entity) {
        return entity.getPart(PositionPart.class);
    }

    private double degreesToRadians(int i) {
        return i * Math.PI / 180;
    }

    protected void spawn(List<CommonSnake> commonSnakeList, GameData gameData) {
        int x = gameData.getDisplayWidth() / 2;
        int y = gameData.getDisplayHeight() / 2;
        int radius;
        if (x > y) {
            radius = x / 3;
        } else {
            radius = y / 3;
        }
        int degrees = 360 / commonSnakeList.size();
        for (int i = 0; i < commonSnakeList.size(); i++) {
            PositionPart positionPart = getEntityPositionPart(commonSnakeList.get(i));
            double radians = degreesToRadians(degrees * (i + 1));
            double xx = x + radius * Math.cos(radians);
            double yy = y + radius * Math.sin(radians);
            positionPart.setY((float) yy);
            positionPart.setX((float) xx);
            positionPart.setRadians((float) radians);
        }
    }
}
