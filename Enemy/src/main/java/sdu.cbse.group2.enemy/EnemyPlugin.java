package sdu.cbse.group2.enemy;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.commonsnake.SnakeSPI;

public class EnemyPlugin implements SnakeSPI, IGamePluginService {

    private CommonSnake enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemySnake(world);
        world.addEntity(enemy);
    }

    // TODO Add Random Spawn
    private CommonSnake createEnemySnake(World world) {
        float maxSpeed = 100;
        float rotationSpeed = 3;
        float x = 200;
        float y = 200;
        float radians = 5;

        CommonSnake enemySnake = new CommonSnake(new GameSprite("enemy/enemy.png", 30, 30), new GameSprite("enemy/tail.png", 30, 30), world);
        enemySnake.add(new MovingPart(maxSpeed, rotationSpeed));
        enemySnake.add(new PositionPart(x, y, radians));

        return enemySnake;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
        enemy.getTailTask().cancel(true);
        enemy.getTail().forEach(world::removeEntity);
    }

    @Override
    public CommonSnake create(GameData gameData, World world) {
        return null;
    }
}
