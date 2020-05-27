package sdu.cbse.group2.powerupspawner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.speedpowerup.SpeedPowerUpPlugin;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowerUpSpawnerTest {

    private PowerUpSpawner powerUpSpawner;
    private World world;
    private GameData gameData;

    @BeforeEach
    public void init() {
        powerUpSpawner = new PowerUpSpawner();
        gameData = new GameData();
        gameData.setDisplayHeight(200);
        gameData.setDisplayWidth(200);
        world = new World(gameData);
        powerUpSpawner.register(new SpeedPowerUpPlugin());
    }

    @Test
    public void powerUpSpawnTest() {
        assertEquals(world.getBoundedEntities(CommonPowerUp.class).size(), 0);
        // Loop 60 frames
        for (int i = 0; i < 60; i++) {
            powerUpSpawner.process(gameData, world);
        }
        assertEquals(world.getBoundedEntities(CommonPowerUp.class).size(), 5);
    }
}