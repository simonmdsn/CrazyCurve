package sdu.cbse.group2.map;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;

public class MapPlugin implements IGamePluginService, IEntityProcessingService {

    private final SnakeTable snakeTable = new SnakeTable();
    private Entity map;

    @Override
    public void start(final GameData gameData, final World world) {
        map = new Entity(new GameSprite("map/background.png", 1000, 1300, -1));
        map.add(new PositionPart(0, 0, 0));
        world.addEntity(map);
        gameData.setDisplayWidth(gameData.getDisplayHeight()); // Quadratic
    }

    @Override
    public void process(final GameData gameData, final World world) {
        world.getBoundedEntities(CommonSnake.class).stream().map(CommonSnake.class::cast).forEach(commonSnake -> snakeTable.updateSnake(commonSnake, gameData, world));
    }

    @Override
    public void stop(final GameData gameData, final World world) {
        world.removeEntity(map);
        snakeTable.clear(world);
    }
}
