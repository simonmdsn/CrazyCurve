package sdu.cbse.group2.enemy;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Enemy enemy;

    @Override
    public void start(GameData gameData, World world) {
        world.addEntity(createEnemySnake());
        world.addEntity(createEnemySnake());
        world.addEntity(createEnemySnake());
    }

    // TODO Add Random Spawn
    private Enemy createEnemySnake() {
        return new Enemy(new GameSprite("enemy/enemy.png", 30, 30), new GameSprite("enemy/tail.png", 30, 30));
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Enemy.class).forEach(world::removeEntity);
    }
}
