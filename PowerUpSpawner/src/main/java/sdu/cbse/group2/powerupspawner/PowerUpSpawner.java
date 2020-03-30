package sdu.cbse.group2.powerupspawner;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonpowerup.PowerUpSPI;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PowerUpSpawner implements IEntityProcessingService, PowerUpSPI {

    private static final int NUMBER_OF_POWER_UPS = 5;

    private final List<Class<? extends CommonPowerUp>> commonPowerUps = new CopyOnWriteArrayList<>();

    @Override
    public void process(GameData gameData, World world) {
        long numberOfPowerUps = world.getEntities(CommonPowerUp.class).size();
        if (numberOfPowerUps >= NUMBER_OF_POWER_UPS) return;
        for (int i = 0; i < NUMBER_OF_POWER_UPS - numberOfPowerUps; i++) {
            try {
                world.addEntity((Entity) commonPowerUps.get(ThreadLocalRandom.current().nextInt(commonPowerUps.size())).getConstructors()[0].newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void register(Class<? extends CommonPowerUp> commonPowerUpClass) {
        commonPowerUps.add(commonPowerUpClass);
    }

    @Override
    public void unregister(Class<? extends CommonPowerUp> commonPowerUpClass) {
        commonPowerUps.remove(commonPowerUpClass);
    }
}
