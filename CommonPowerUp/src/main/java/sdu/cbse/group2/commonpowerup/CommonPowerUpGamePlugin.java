package sdu.cbse.group2.commonpowerup;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.common.services.IGamePluginService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CommonPowerUpGamePlugin implements IGamePluginService, IEntityProcessingService {

    private static final int NUMBER_OF_POWER_UPS = 5;
    private final List<CommonPowerUp> commonPowerUps = new CopyOnWriteArrayList<>();

    public void addPowerUp(CommonPowerUp commonPowerUp) {
        commonPowerUps.add(commonPowerUp);
    }

    public void removePowerUp(CommonPowerUp commonPowerUp) {
        commonPowerUps.remove(commonPowerUp);
    }

    @Override
    public void start(GameData gameData, World world) {
        world.addEntity();
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity();
    }

    @Override
    public void process(GameData gameData, World world) {
        long numberOfPowerUps = world.getEntities(CommonPowerUp.class).size();
        if (numberOfPowerUps >= NUMBER_OF_POWER_UPS) return;
        for (int i = 0; i < NUMBER_OF_POWER_UPS - numberOfPowerUps; i++) {
            CommonPowerUp commonPowerUp = commonPowerUps.get(ThreadLocalRandom.current().nextInt(commonPowerUps.size()));
            world.addEntity(commonPowerUp);
        }
    }
}
