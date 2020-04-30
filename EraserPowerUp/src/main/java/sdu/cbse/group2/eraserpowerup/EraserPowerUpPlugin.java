package sdu.cbse.group2.eraserpowerup;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonpowerup.PowerUpSPI;

public class EraserPowerUpPlugin implements IGamePluginService, PowerUpSPI {

    private World world;

    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(EraserPowerUp.class).forEach(world::removeEntity);
    }

    @Override
    public CommonPowerUp spawn(int x, int y) {
        return new EraserPowerUp(x,y, world);
    }
}
