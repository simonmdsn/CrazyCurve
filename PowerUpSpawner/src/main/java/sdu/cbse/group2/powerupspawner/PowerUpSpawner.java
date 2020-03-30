package sdu.cbse.group2.powerupspawner;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonpowerup.PowerUpSPI;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PowerUpSpawner implements IEntityProcessingService {

    private static final int NUMBER_OF_POWER_UPS = 5;
    private static final List<PowerUpSPI> commonPowerUps = new CopyOnWriteArrayList<>();

    @Override
    public void process(GameData gameData, World world) {
        long numberOfPowerUps = world.getBoundedEntities(CommonPowerUp.class).size();
        if (numberOfPowerUps >= NUMBER_OF_POWER_UPS || commonPowerUps.isEmpty()) return;
        for (int i = 0; i < NUMBER_OF_POWER_UPS - numberOfPowerUps; i++) {
            world.addEntity(commonPowerUps.get(ThreadLocalRandom.current().nextInt(commonPowerUps.size())).spawn());
        }
    }

    public void register(PowerUpSPI powerUpSPI) {
        commonPowerUps.add(powerUpSPI);
    }

    public void unregister(PowerUpSPI powerUpSPI) {
        commonPowerUps.remove(powerUpSPI);
    }

    //TODO remove all powerups
    private void removeAll() {}
}
