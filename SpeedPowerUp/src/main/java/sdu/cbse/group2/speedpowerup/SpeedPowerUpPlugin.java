package sdu.cbse.group2.speedpowerup;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonpowerup.PowerUpSPI;

public class SpeedPowerUpPlugin implements IGamePluginService {

    private PowerUpSPI powerUpSPI;

    public SpeedPowerUpPlugin(PowerUpSPI powerUpSPI) {
        this.powerUpSPI = powerUpSPI;
    }

    @Override
    public void start(GameData gameData, World world) {
        System.out.println("YOOOOOOOOOOOOOOOOOOOOOO");
        System.out.print(powerUpSPI);
        //powerUpSPI.register(SpeedPowerUp.class);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(SpeedPowerUp.class).forEach(world::removeEntity);
//        powerUpSPI.unregister(SpeedPowerUp.class);
    }

//    public void removePowerUpSPI() {
//        powerUpSPI = null;
//    }

//    public void setPowerUpSPI(PowerUpSPI powerUpSPI) {
//        this.powerUpSPI = powerUpSPI;
//        System.out.println(powerUpSPI);
//    }
}
