package sdu.cbse.group2.eraserpowerup;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.List;

public class EraserPowerUp extends CommonPowerUp {

    private World world;

    public EraserPowerUp(int x, int y, World world) {
        super(new GameSprite("textures/powerup/eraser.png", 30, 30));
        this.add(new PositionPart(x, y, 0));
        this.world = world;
    }

    @Override
    public void applyPowerUp(CommonSnake commonSnake) {
        List<Entity> commonSnakes = world.getBoundedEntities(CommonSnake.class);
        commonSnakes.forEach(entity -> ((CommonSnake) entity).deleteTail(world));
    }

    @Override
    public void removePowerUp(CommonSnake commonSnake) {

    }
}
