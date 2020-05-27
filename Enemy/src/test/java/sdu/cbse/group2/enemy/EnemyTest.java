package sdu.cbse.group2.enemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EnemyTest {

    private EnemyControlSystem enemyControlSystem;
    private World world;
    private GameData gameData;

    @BeforeEach
    public void init() {
        enemyControlSystem = new EnemyControlSystem();
        gameData = new GameData();
        gameData.setDisplayHeight(200);
        gameData.setDisplayWidth(200);
        world = new World(gameData);
    }

    @Test
    public void createAndMoveEnemy() {
        Enemy enemy = new Enemy(new GameSprite("", 0, 0), new GameSprite("", 0, 0), "test-snake");
        PositionPart initPosition = new PositionPart(100, 100, 0);
        float x = initPosition.getX();
        float y = initPosition.getY();
        enemy.add(initPosition);
        world.addEntity(enemy);
        // Process 60 frames
        for (int i = 0; i < 60; i++) {
            gameData.setDelta(i);
            enemyControlSystem.process(gameData, world);
        }
        PositionPart finalPosition = enemy.getPart(PositionPart.class);
        assertNotEquals(x, finalPosition.getX());
        assertNotEquals(y, finalPosition.getY());
    }
}