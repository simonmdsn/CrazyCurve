package sdu.cbse.group2.map;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;

public class MapProcessor implements IEntityProcessingService {

    private final SnakeTable snakeTable = new SnakeTable();

    @Override
    public void process(final GameData gameData, final World world) {
        world.getBoundedEntities(CommonSnake.class).stream().map(CommonSnake.class::cast).forEach(commonSnake -> snakeTable.updateSnake(commonSnake, gameData, world));
    }
}
