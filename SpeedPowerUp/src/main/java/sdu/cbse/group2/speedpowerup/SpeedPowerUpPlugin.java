package sdu.cbse.group2.speedpowerup;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonpowerup.PowerUpSPI;

public class SpeedPowerUpPlugin implements IGamePluginService, PowerUpSPI {


    private GameData gameData;

    @Override
    public void start(GameData gameData, World world) {
        this.gameData = gameData;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(SpeedPowerUp.class).forEach(world::removeEntity);
    }

    @Override
    public CommonPowerUp spawn() {
        return new SpeedPowerUp(gameData);
    }
}
