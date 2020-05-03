package sdu.cbse.group2.powerupspawner;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.data.entityparts.TimerPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonpowerup.PowerUpSPI;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PowerUpSpawner implements IEntityProcessingService {

    private static final int NUMBER_OF_POWER_UPS = 5;
    private static final List<PowerUpSPI> commonPowerUps = new CopyOnWriteArrayList<>();

    @Override
    public void process(GameData gameData, World world) {
        List<CommonPowerUp> powerUpEntities = world.getBoundedEntities(CommonPowerUp.class).stream().map(CommonPowerUp.class::cast).collect(Collectors.toList());
        for(CommonPowerUp commonPowerUp: powerUpEntities) {
            TimerPart timerPart = commonPowerUp.getPart(TimerPart.class);
            timerPart.process(gameData,commonPowerUp);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(commonPowerUp);
            }
        }
        if (powerUpEntities.size() >= NUMBER_OF_POWER_UPS || commonPowerUps.isEmpty()) return;
        for (int i = 0; i < NUMBER_OF_POWER_UPS - commonPowerUps.size(); i++) {
            PositionPart emptyTilePositionPart = world.getRandomEmptyTile().getPositionPart();
            CommonPowerUp commonPowerUp = commonPowerUps.get(ThreadLocalRandom.current().nextInt(commonPowerUps.size())).spawn((int)emptyTilePositionPart.getX(),(int)emptyTilePositionPart.getY());
            commonPowerUp.add(new TimerPart(getRandomExpirationTime(14,20)));
            commonPowerUp.setObstructing(false);
            world.addEntity(commonPowerUp);
        }
    }

    public int getRandomExpirationTime(int lowerBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
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
