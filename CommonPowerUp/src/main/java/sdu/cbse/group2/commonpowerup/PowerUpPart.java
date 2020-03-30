package sdu.cbse.group2.commonpowerup;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.entityparts.EntityPart;

import java.util.ArrayList;
import java.util.List;

public class PowerUpPart implements EntityPart {

    private List<CommonPowerUp> currentPowerUps = new ArrayList<>();

    public void addPowerUp(CommonPowerUp commonPowerUp) {
        currentPowerUps.add(commonPowerUp);
    }

    public void removePowerUp(CommonPowerUp commonPowerUp) {
        currentPowerUps.remove(commonPowerUp);
    }

    public boolean contains(Class<? extends CommonPowerUp> commonPowerUpClass) {
        return currentPowerUps.stream().anyMatch(commonPowerUp -> commonPowerUp.getClass().equals(commonPowerUpClass));
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
