package sdu.cbse.group2.turtlepowerup;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonpowerup.PowerUpSPI;

public class TurtlePowerUpPlugin implements IGamePluginService, PowerUpSPI {

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(TurtlePowerUp.class).forEach(world::removeEntity);
    }

    @Override
    public CommonPowerUp spawn(int x, int y) {
        return new TurtlePowerUp(x, y);
    }
}
