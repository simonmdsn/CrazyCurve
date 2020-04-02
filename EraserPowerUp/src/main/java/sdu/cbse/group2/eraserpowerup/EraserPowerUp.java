package sdu.cbse.group2.eraserpowerup;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EraserPowerUp extends CommonPowerUp {

    public EraserPowerUp(GameData gameData) {
        super(new GameSprite("powerup/eraser.png",30,30));
        this.add(new PositionPart(ThreadLocalRandom.current().nextInt(gameData.getDisplayWidth()), ThreadLocalRandom.current().nextInt(gameData.getDisplayHeight()), 0));
    }

    public void applyPowerUp(CommonSnake commonSnake) {
        World world = commonSnake.getWorld();
        List<Entity> commonSnakes = commonSnake.getWorld().getEntities(CommonSnake.class);
        commonSnakes.forEach(entity -> ((CommonSnake) entity).getTail().forEach( tail -> world.removeEntity(tail)));
        commonSnakes.forEach(entity -> ((CommonSnake) entity).getTail().clear());
    }

    @Override
    public void removePowerUp(CommonSnake commonSnake) {

    }
}
